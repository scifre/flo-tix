function onOpen() {
  var ui = SpreadsheetApp.getUi();
  ui.createMenu("Send Tickets")
    .addItem("Send Emails", "uploadDataToFirestore")
    .addToUi();
}

function generateSHA256(input) {
  var rawHash = Utilities.computeDigest(Utilities.DigestAlgorithm.SHA_256, input);
  var hashString = rawHash.map(byte => ('0' + (byte & 0xFF).toString(16)).slice(-2)).join('');
  return hashString;
}


function uploadDataToFirestore() {
  var sheet = SpreadsheetApp.getActiveSpreadsheet().getActiveSheet();
  var data = sheet.getDataRange().getValues();

  var ticketTemplate = DriveApp.getFileById(/*Your File ID*/).getBlob();

  //separating headers and data
  var headers = data[0];
  var rows = data.slice(1);

  //firestore database url
  var firestoreUrl = //Your firestore URL

  //loop thru all rows
  for(var i=0; i<rows.length; i++){
      var row = rows[i];
      Logger.log(row);
    //detect new row
    if(row[headers.indexOf("payment status")] == "captured" && row[headers.indexOf("Data Uploaded")] == ""){
      //payment id used as doc id
      //encode payment id
      var docID = Utilities.base64Encode(row[headers.indexOf("payment id")]);

      //json to send to firestore
      var firestoreData = {
        fields: {
          name: {stringValue: row[headers.indexOf("full_name")].toString()},
          college: {stringValue: row[headers.indexOf("college_or_organization")].toString()},
          email: {stringValue: row[headers.indexOf("email")].toString()},
          phone: {stringValue: row[headers.indexOf("phone")].toString()},
          payment_id: {stringValue: row[headers.indexOf("payment id")].toString()},
          passtype: {stringValue: row[headers.indexOf("payment page title")].toString()},
          scanned: {booleanValue: false}
        }
      }

      //send options
      var options = {
        method: "PATCH",
        contentType: "application/json",
        payload: JSON.stringify(firestoreData),
        muteHttpException: true
      };

      //sending to firestore
      var url = firestoreUrl + "/" + docID;
      var response = UrlFetchApp.fetch(url, options);
      Logger.log(response.getContentText());



      //get qr code
      var paymentIDhash = docID;
      var qrUrl = "https://quickchart.io/qr?text=" + encodeURIComponent(paymentIDhash)+"&margin=1";
      var qrBlob = UrlFetchApp.fetch(qrUrl).getBlob().setName("QRCode.png");

      //sending email
      var email = row[headers.indexOf("email")];

      var emailImages = {"template":ticketTemplate, "qrCode": qrBlob};

      var subject = "Technovate 6.0 Online Pass & Important Event Details"

      var htmlbody = `
      <body style="font-family: Arial, sans-serif; line-height: 1.6; color: #333;">

      <h2>Greetings from Technovate!</h2>
      <p>Hello, ${row[headers.indexOf("full_name")]}!</p>
      <p>Thank you for registering for <b>Technovate 6.0</b>, the annual techno-cultural fest of IIIT Naya Raipur, happening from <b>March 21st to 23rd, 2025</b>. Attached is your online pass, which you must present at the entry gate along with a valid government-issued ID/college ID for access to the event.</p>

      <h3>&#128197; Event Details:</h3>
      <ul>
          <li><b>&#128197; Dates:</b> March 21st – March 23rd, 2025</li>
          <li><b>&#128205; Venue:</b> IIIT Naya Raipur Campus (except for Day 3)</li>
          <li><b>&#127926; Day 3 (Artist Night) Venue:</b> Open ground opposite BALCO Medical Center, Near Purkhouti Muktangan, Sector 27, 493661, Naya Raipur.</li>
      </ul>

      <h3>&#9888; Important Ticket & Event Rules:</h3>
      <ul>
          <li> Each ticket is valid for <b>one person only</b> and must be shown at the entry gate.</li>
          <li> Tickets are <b>non-refundable, non-transferable,</b> and cannot be exchanged.</li>
          <li> Entry is allowed only with a <b>valid ticket</b> and a <b>government-issued ID/college ID</b>.</li>
          <li> The organizer reserves the right to <b>deny entry</b> without explanation.</li>
          <li> Bags and personal belongings may be subject to <b>security checks</b>.</li>
          <li> Misconduct, unruly behavior, or intoxication will lead to <b>removal</b> without a refund.</li>
          <li> The organizer is not responsible for <b>personal injury, loss, or theft</b> of belongings.</li>
          <li> In case of event cancellation, refunds (if applicable) will be processed after <b>GST & platform fee deduction</b>.</li>
          <li> Outside food, beverages, illegal substances, weapons, and hazardous materials are <b>strictly prohibited</b>.</li>
          <li> Smoking and alcohol consumption are <b>restricted as per venue policies</b>.</li>
          <li> Neither IIIT Naya Raipur nor the sponsors promote <b>any form of vulgarity</b>.</li>
      </ul>

      <p>Please ensure you comply with all event rules to help us maintain a <b>safe and enjoyable experience</b> for everyone.</p>

      <p>For any queries, feel free to reach out to us. We look forward to welcoming you to <b>Technovate 6.0!</b></p>

      <p><b>Best Regards,</b><br>
      <b>Technovate 6.0 Team</b><br>
      IIIT Naya Raipur</p>

      <!-- Ticket Section -->
      <div style="width: 95%; text-align: center; border: 2px solid black; padding: 10px; margin: 20px auto;">
          <h3>&#127903; <b>This is your ticket.</b> &#127903;</h3>
          <div style="
              width: 100%;
              display: flex;
              align-items: center;
              border: 2px solid black;
          ">
              <!-- Ticket Template -->
              <div style="width: 71%; border: 2px solid black;">
                  <img src="cid:template" alt="Ticket Template" style="width: 100%; height: auto;">
              </div>

              <!-- QR Code -->
              <div style="width: 29%; border: 2px solid black;">
                  <img src="cid:qrCode" alt="QR Code" style="width: 100%; height: auto;">
              </div>
          </div>
      </div>
    </body>
      `


    GmailApp.sendEmail(
      email,
      subject,
      "",
      {
        htmlBody: htmlbody,
        inlineImages: emailImages
      }
    );
      var uploadedIndex = headers.indexOf("Data Uploaded");
      sheet.getRange(i+2, uploadedIndex+1).setValue("Done");

    }
  };

  Logger.log("data upload successful")

}

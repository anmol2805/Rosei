const functions = require('firebase-functions');
const admin = require('firebase-admin');
admin.initializeApp(functions.config().firebase);
var today = new Date();
var curry = today.getFullYear();
var lasty = curry - 4;
var i;

exports.pushNotification = functions.database.ref('/send_reminder').onWrite( event => {

    console.log('date changed');
    const payload = {
        notification: {
            title: "Reminder for mess coupons",
            body: "Book your online mess coupons before thursday night and do collect by saturday evening. Ignore if already booked",
            sound: "default"
        },
    };
    const options = {
        priority: "high",
        timeToLive: 60 * 60 * 24
    };
    for(i=lasty;i<=curry;i++){
        admin.messaging().sendToTopic(i.toString().slice(2),payLoad,options);
    }

return;
});

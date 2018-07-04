
(function () {
    
    var awsCredentials = new AWS.Credentials("aws_access_key_id", "aws_secret_access_key");
    var settings = {
        awsCredentials: awsCredentials,
        awsRegion: "us-west-2",
        pollyVoiceId: "Justin",
        cacheSpeech: true
    }
    var kathy = ChattyKathy(settings);
    
    kathy.Speak("<speak>Hello world, my name is Mini-cat! Pleasure to meet you.</speak>");
    kathy.Speak("<speak><prosody rate=\"slow\"><prosody pitch=\"+20%\"><break time = \"200ms\"/>In some cases, it might help your audience to slow the speaking rate slightly to aid in comprehension.</prosody></prosody></speak>");

    if (kathy.IsSpeaking()) {
        kathy.ShutUp(); 
    }

    kathy.ForgetCachedSpeech();
})();


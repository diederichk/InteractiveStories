"""This was taken from the AWS Python Example, and modified for Story Carnival"""
"""Getting Started Example for Python 2.7+/3.3+"""
# Create a client using the credentials and region defined in the [adminuser]
# section of the AWS credentials file (~/.aws/credentials).
from boto3 import Session
from botocore.exceptions import BotoCoreError, ClientError
from contextlib import closing
from pydub import AudioSegment
import os
import sys
import subprocess
from tempfile import gettempdir
import time

## pydub: https://github.com/jiaaro/pydub
## MAKE SURE TO FOLLOW DIRECTIONS FOR INSTALLING DEPENDENCES

# FUNCTION
# generatePollyAudio - generates an MP3 from a stream to Amazon Polly
# PARAMETERS
# message - the message you want to send to Amazon Polly.
# voice_ID - the name of the voice you want to use. Only need the name, no country {Joanna, Geraint, etc.}
# file_name - the output file name. Must be able to be reached from the current directory
# input_type - text or ssml. depends on your message structure
# RETURN
# None. Creates a audio file at the specified location
# NOTE: you will need to make sure the profile_name is correct to start a session
def generate_polly_audio(message, voice_ID, file_name, input_type="text"):
  session = Session(profile_name="adminuser")
  polly = session.client("polly")

  try:
      # Request speech synthesis
      response = polly.synthesize_speech(Text=message, OutputFormat="mp3", TextType=input_type,
                                          VoiceId=voice_ID)
  except (BotoCoreError, ClientError) as error:
      # The service returned an error, exit gracefully
      print(error)
      sys.exit(-1)

  # Access the audio stream from the response
  if "AudioStream" in response:
      # Note: Closing the stream is important as the service throttles on the
      # number of parallel connections. Here we are using contextlib.closing to
      # ensure the close method of the stream object will be called automatically
      # at the end of the with statement's scope.
      with closing(response["AudioStream"]) as stream:
          output = os.path.join(os.getcwd(),file_name)

          try:
              # Open a file for writing the output as a binary stream
              with open(output, "wb") as file:
                  file.write(stream.read())
          except IOError as error:
              # Could not write to file, exit gracefully
              print(error)
              sys.exit(-1)

  else:
      # The response didn't contain audio data, exit gracefully
      print("Could not stream audio")
      sys.exit(-1)


def play_polly_aduio(file_name):
  # Play the audio using the platform's default player
  if sys.platform == "win32":
      os.startfile(output)
  else:
      # the following works on Mac and Linux. (Darwin = mac, xdg-open = linux).
      opener = "open" if sys.platform == "darwin" else "xdg-open"
      subprocess.call([opener, file_name])


# FUNCTION - get_voices_dict()
def get_voices_dict(input_file, comment_char = "#"):
    voices = dict()
    with open(os.path.join(os.getcwd(), input_file), 'r') as f:
        x = "buffer"
        # keep reading until nothing comes in
        while x:
            x = f.readline()
            if x == "" or x[0] == comment_char:
                continue
            char,voice = x.strip(" \n").split(":")
            voices[char] = voice

    return voices

# FUNCTION - get_voices_dict()
def get_slides_dict(input_file, comment_char = "#"):
    slides = {}
    with open(os.path.join(os.getcwd(), input_file), 'r') as f:
        x = "buffer"
        # keep reading until nothing comes in
        while x:
            x = f.readline()
            if x == "" or x[0] == comment_char:
                continue
            n,char,message = x.strip("\n").split(":")
            
            if n not in slides:
                slides[n] = []

            slides[n].append((char, message))

    return slides


# FUNCTION - get_script_for_polly
# takes an input script file and turns it into an array of messages
def get_script_array(input_file, voices, comment_char = "#", input_type="text"):
    
    with open(input_file, 'r') as f:
        x = f.readline()

    return "hello"


def join_audio_with_pause(fnames, out_file, pause_len = 120):
    pause = AudioSegment.silent(duration=pause_len)   
    final_audio = AudioSegment.silent(duration=1)

    for f in fnames:
        audio = AudioSegment.from_mp3(os.path.join(os.getcwd(),f))
        final_audio = final_audio + audio + pause

    final_audio.export(os.path.join(os.getcwd(),out_file), format="mp3")
    


def convert_to_ssml(text_message, use_break, speed):
    ssml_mess = "<amazon:breath/>".join(text_message.split(","))
    ssml_mess = '<speak><prosody rate="90%" pitch="+30%">' + ssml_mess + '</prosody></speak>'
    print(ssml_mess)
    return ssml_mess





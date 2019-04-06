
import SCPollyLib as p
import time

# get the voices from the input file
# this function defaults to inputs/voices.txt, but can be overridden
voices = p.get_voices_dict(input_file = "inputs/voices.txt")
slides = p.get_slides_dict(input_file = "inputs/polly_script.txt")

for slidenum in slides:
  f_names = []
  f_num = "0" + slidenum if int(slidenum) < 10 else slidenum

  out_name = "slide_audio/slide{}.mp3".format(f_num)

  for i,subslide in enumerate(slides[slidenum]):
     # Create the individual character audio clip file names
     f_name = "audio_clips/slide{}-{}.mp3".format(f_num,i)
     f_names.append(f_name)
     
     # Define variables for Polly
     char = subslide[0]
     mess = p.convert_to_ssml(subslide[1], use_break=True, speed = 100)

     p.generate_polly_audio(message=mess, voice_ID=voices[char], file_name=f_name, input_type="ssml")
  
  # join the subclips together with silence, and play it (use sleep to wait for completion)
  p.join_audio_with_pause(f_names,out_file = out_name, pause_len=300)
  # p.play_polly_aduio(out_name)
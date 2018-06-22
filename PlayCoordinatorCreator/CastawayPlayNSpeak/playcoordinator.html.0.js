var stage;
var bg;				// Background
var imgqueue;		// Queue for all images
var musicqueue;		// Queue for all sounds
var container, bmp;	// Container object, image object
var roles = []; // role buttons
var people = [{name: "Player1", role: -1}, {name: "Player2", role: -1}, {name: "Player3", role: -1}, {name: "Player4", role: -1}];
var results = []; // for displaying people and roles chosen

// Creating a queue to manage click events
var taskQueue = [];		// Simply an array that can be turned into a queue
//queue.push;			// Method call for adding to a queue
//queue.shift;			// Method call for removing from the queue

var title; // text which indicates who needs to pick

var musicPath = "Story_Audio/";
var imgPath = "Story_Images/";
var IMAGE_WIDTH = 2200;
var IMAGE_HEIGHT = 1238;

var NUM_ROLES = 12; // number of roles to chose from

//variables for current slide,music playing, person picking role
var song = 0;
var person = 0;
var slide = 0;
var resultSlide = 1;
var current_role = -1;
var pickComplete = false;	// Flag to determine when roles have all been picked
							// When true move to allow four category button pressing

// Sound Variables
var src;            // the audio src we are trying to play
var soundInstance;  // the soundInstance returned by Sound when we create or play a src
soundInstance.addEventListener('complete', playQueueSound);
var playingSound = false;
var canvas;			// the canvas we draw to

// Voiced Actor Variables
var ideaAmt;		// The about of idea audio files.
var contextAmt;		// The about of context audio files.
var reactAmt;		// The about of reaction audio files.
var aboutAmt;		// The about of about audio files.

var nextArrow, prevArrow;
var nextShape, prevShape;

function resize() {	
	var w = window.innerWidth-2;
	var h = window.innerHeight-2;

	stage.canvas.width = w;
	stage.canvas.height = h;

	// Simple "fit-to-screen" scaling
	var ratio = IMAGE_WIDTH / IMAGE_HEIGHT; // Use the "default" size of the content you have.
	var windowRatio = w/h;
	
	// calculate a scale factor to keep a correct aspect ratio.			
	var scale = w/IMAGE_WIDTH;
	if (windowRatio > ratio) {
		scale = h/IMAGE_HEIGHT;
	}
	
	// Background: full screen redraw 
	bg.graphics.clear();
	bg.graphics.beginFill("#222").drawRect(0, 0, stage.canvas.width, stage.canvas.height);
	
	// Content: centered
	container.x = stage.canvas.width / 2;
	container.y = stage.canvas.height / 2; 
	
	// Scale up to fit width or height
	container.scaleX = container.scaleY = scale;
	
	stage.update();
}

function init() {
	// this does two things, it initializes the default plugins, and if that fails the if statement triggers and we display an error
	
	// Resize event listener
	window.addEventListener('resize', resize);
	
	// Assign default values
	musicPath = "Story_Audio/";
	imgPath = "Story_Images/";
	IMAGE_WIDTH = 2200;
	IMAGE_HEIGHT = 1238;
	NUM_ROLES = 12; // number of roles to chose from
	song = 0;
	person = 0;
	slide = 0;
	resultSlide = 1;
	current_role = -1;
	pickComplete = false;	// Flag to determine when roles have all been picked
							// When true move to allow four category button pressing
	ideaAmt = 9;		// The about of idea audio files.
	contextAmt = 8;		// The about of context audio files.
	reactAmt = 10;		// The about of reaction audio files.
	aboutAmt = 12;		// The about of about audio files.

	
	// if initializeDefaultPlugins returns false, we cannot play sound in this browser
	if (!createjs.Sound.initializeDefaultPlugins()) {return; }
		
	// create a new stage and point it at our canvas:
	canvas = document.getElementById("demoCanvas");
	stage = new createjs.Stage(canvas);
	
	// Create the background
	createBackground();
	
	var musicManifest = [
		{id: "0", src: musicPath + "audio_00.mp3"},
		{id: "1", src: musicPath + "audio_01.mp3"},
		{id: "2", src: musicPath + "audio_02.mp3"},
		{id: "3", src: musicPath + "audio_03.mp3"},
		{id: "4", src: musicPath + "audio_04.mp3"},
		{id: "a1", src: musicPath + "about_01.mp3"},
		{id: "a2", src: musicPath + "about_02.mp3"},
		{id: "a3", src: musicPath + "about_03.mp3"},
		{id: "a4", src: musicPath + "about_04.mp3"},
		{id: "a5", src: musicPath + "about_05.mp3"},
		{id: "a6", src: musicPath + "about_06.mp3"},
		{id: "a7", src: musicPath + "about_07.mp3"},
		{id: "a8", src: musicPath + "about_08.mp3"},
		{id: "a9", src: musicPath + "about_09.mp3"},
		{id: "a10", src: musicPath + "about_10.mp3"},
		{id: "a11", src: musicPath + "about_11.mp3"},
		{id: "a12", src: musicPath + "about_12.mp3"},
		{id: "c1", src: musicPath + "context_01.mp3"},
		{id: "c2", src: musicPath + "context_02.mp3"},
		{id: "c3", src: musicPath + "context_03.mp3"},
		{id: "c4", src: musicPath + "context_04.mp3"},
		{id: "c5", src: musicPath + "context_05.mp3"},
		{id: "c6", src: musicPath + "context_06.mp3"},
		{id: "c7", src: musicPath + "context_07.mp3"},
		{id: "c8", src: musicPath + "context_08.mp3"},
		{id: "i1", src: musicPath + "idea_01.mp3"},
		{id: "i2", src: musicPath + "idea_02.mp3"},
		{id: "i3", src: musicPath + "idea_03.mp3"},
		{id: "i4", src: musicPath + "idea_04.mp3"},
		{id: "i5", src: musicPath + "idea_05.mp3"},
		{id: "i6", src: musicPath + "idea_06.mp3"},
		{id: "i7", src: musicPath + "idea_07.mp3"},
		{id: "i8", src: musicPath + "idea_08.mp3"},
		{id: "i9", src: musicPath + "idea_09.mp3"},
		{id: "r1", src: musicPath + "react_01.mp3"},
		{id: "r2", src: musicPath + "react_02.mp3"},
		{id: "r3", src: musicPath + "react_03.mp3"},
		{id: "r4", src: musicPath + "react_04.mp3"},
		{id: "r5", src: musicPath + "react_05.mp3"},
		{id: "r6", src: musicPath + "react_06.mp3"},
		{id: "r7", src: musicPath + "react_07.mp3"},
		{id: "r8", src: musicPath + "react_08.mp3"},
		{id: "r9", src: musicPath + "react_09.mp3"},
		{id: "r10", src: musicPath + "react_10.mp3"},
	];
	
	// Instantiate a music queue.
	musicqueue = new createjs.LoadQueue();
	createjs.Sound.alternateExtensions = ["mp3"];	// add other extensions to try loading if the src file extension is not supported
	musicqueue.installPlugin(createjs.Sound);
	musicqueue.addEventListener("complete", loadComplete);
	musicqueue.loadManifest(musicManifest);
	
	
	// Royalty free sound clips: http://www.bigsoundbank.com/
	// Royalty free music songs: http://www.bensound.com/
	
	//canvas.innerHTML = "Waiting for load to complete.";  // letting the user know what's happening
	
	
	// Method: Display a slideshow
	imgqueue = new createjs.LoadQueue(false); // Creates a new queue (false is important)
	imgqueue.setMaxConnections(10); // Allows 10 concurrent connections for load
	
	// Need to call container creator
	imgqueue.addEventListener("complete", createContainer); // Calls handleCmplt when load is finished
	
	imgqueue.loadManifest([
		{id: '0', src: imgPath + 'story1-newratio-00.png'},
		{id: '1', src: imgPath + 'story1-newratio-01.png'},
		{id: '2', src: imgPath + 'story1-newratio-02.png'},
		{id: '3', src: imgPath + 'story1-newratio-03.png'},
		{id: '4', src: imgPath + 'story1-newratio-04.png'},
		{id: '5', src: imgPath + 'story1-newratio-05.png'},
		{id: '6', src: imgPath + 'story1-newratio-06.png'},
		{id: '7', src: imgPath + 'story1-newratio-07.png'},
		{id: '8', src: imgPath + 'story1-newratio-08.png'},
		{id: '9', src: imgPath + 'story1-newratio-09.png'},
		{id: '10', src: imgPath + 'story1-newratio-10.png'},
		{id: '11', src: imgPath + 'story1-newratio-11.png'},
		{id: '12', src: imgPath + 'story1-newratio-12.png'},
		{id: '13', src: imgPath + 'story1-newratio-13.png'},
		{id: '14', src: imgPath + 'story1-newratio-14.png'},
		{id: '15', src: imgPath + 'story1-newratio-15.png'},
		{id: '16', src: imgPath + 'story1-newratio-16.png'},
		{id: '17', src: imgPath + 'story1-newratio-17.png'},
		{id: '18', src: imgPath + 'story1-newratio-18.png'},
		{id: '19', src: imgPath + 'story1-newratio-19.png'},
		{id: '20', src: imgPath + 'story1-newratio-20.png'},
		{id: 'role1', src: imgPath + 'role1.png'},
		{id: 'role2', src: imgPath + 'role2.png'},
		{id: 'role3', src: imgPath + 'role3.png'},
		{id: 'role4', src: imgPath + 'role4.png'},
		{id: 'role5', src: imgPath + 'role5.png'},
		{id: 'role6', src: imgPath + 'role6.png'},
		{id: 'role7', src: imgPath + 'role7.png'},
		{id: 'role8', src: imgPath + 'role8.png'},
		{id: 'role9', src: imgPath + 'role9.png'},
		{id: 'role10', src: imgPath + 'role10.png'},
		{id: 'role11', src: imgPath + 'role11.png'},
		{id: 'role12', src: imgPath + 'role12.png'},
		{id: 'right', src: imgPath + 'right_arrow.png'},
		{id: 'left', src: imgPath + 'left_arrow.png'},
		{id: 'check', src: imgPath + 'check_arrow.png'},
		{id: 'dragright', src: imgPath + 'drag_right.png'}
	]); // Preloads all images, Images paired with number id tags
	
	// Obtain a random order of the input names.
	people = shuffle(people);
	
	resize();
}

// Added to create a random order to have names appear
function shuffle(array) {
	var currentIndex = array.length, temporaryValue, randomIndex;
	// While there remain elements to shuffle...
	while (0 !== currentIndex) {

		// Pick a remaining element...
		randomIndex = Math.floor(Math.random() * currentIndex);
		currentIndex -= 1;
		
		// And swap it with the current element.
		temporaryValue = array[currentIndex];
		array[currentIndex] = array[randomIndex];
		array[randomIndex] = temporaryValue;
	}
	return array;
}

//create and display the background (fullscreen)
function createBackground() {
	bg = new createjs.Shape();
	stage.addChild(bg);
}

function createContainer() {
	
	console.log("CONTAINER CREATED!");
	// Obtain the current draw space
	contentWidth = document.getElementById("demoCanvas").offsetWidth;
	contentHeight = document.getElementById("demoCanvas").offsetHeight;

	container = new createjs.Container();
	stage.addChild(container);
			
	// Load the Image
	storyImg = new Image();
	storyImg.src = imgPath + "story1-newratio-00.png";
	storyImg.onload = handleImageLoad;
}

function handleImageLoad() {
	// Create a CreateJS bitmap from the loaded image
	var bmpStoryImage = new createjs.Bitmap(storyImg);
	
	// Add the bitmap to the Container
	container.addChild(bmpStoryImage);
	
	// Set the scale value
	// It could be useful to properly handle different mobile resolutions
	container.scaleX = 0.5;
	container.scaleY = 0.5;
					
	//draw buttons for picking roles and back button
	addRoleButtons();
	addBackButton();
					
	// Set the registration point of the content Container to center
	container.regX = bmpStoryImage.image.width / 2;
	container.regY = bmpStoryImage.image.height / 2;
	
	//stage.update();		
	resize();
}

//create role buttons and title text and wait for clicks
function addRoleButtons() {
	var match = false;	// Used to determine if a role has been previously chosen
	
	for( var i = 0; i < NUM_ROLES; i++) {
		
		// Add logic to check if a role is assigned to a certain person, then do not display the role
		// Loop to determine if match 
		for(var j = 0; j < people.length; j++) {
			if( i == people[j].role) {
				match = true;
			}
		}
		if (match) {
			match = false;
			continue;
		} //End of matching
		
		var button = new createjs.Bitmap(imgqueue.getResult("role"+(i+1)));
		button.name = i;
		button.x = (i%4)*400+270;
		button.y = Math.floor(i/4)*320+200;
	
		var backShape = new createjs.Shape();
		backShape.graphics.beginFill("#ffffff").drawRoundRect(0, 0, 400, 400, 30);
		backShape.x = 40;
		backShape.y = 40;
		backShape.alpha = 0.01;
		
		button.addEventListener("click", roleClick);
		button.hitArea = backShape;
		
		container.addChild(button);
		container.setChildIndex(button,1);
		
		roles[i] = button;
		//container.setChildIndex(title,2);
	}
	title = new createjs.Text("Pass to: "+ people[person].name, "80px Comic Sans MS", "#ff7700");
	title.x = 330;
	title.y = 180;
	title.textBaseline = "alphabetic";
    
	container.addChild(title);
}

//create back button and background shape
function addBackButton(){
	backArrow = new createjs.Bitmap(imgqueue.getResult("left"));
	backArrow.x = 40;
	backArrow.y = 40;
	backArrow.addEventListener("click", prvClck);/*BUG*/	// Including this following line of code will cause the images to display
															// but will prevent all event listeners from functioning.
	container.addChild(backArrow);
	container.setChildIndex(backArrow, 1);
	
	backShape = new createjs.Shape();
	backShape.graphics.beginFill("#ffffff").drawRoundRect(0, 0, 270, IMAGE_HEIGHT - 140, 30);  // Original orange color: #ff8c00
	//prvShape.graphics.beginStroke("#000000").setStrokeStyle(3).drawRoundRect(0, 0, 270, IMAGE_HEIGHT - 140, 30);
	backShape.x = 40;
	backShape.y = 40;
	backShape.alpha = 0.01;
	//prvShape.shadow = new createjs.Shadow("#000000", 5, 5, 10);
	backShape.addEventListener("click", prvClck);
	
	container.addChild(backShape);
	container.setChildIndex(backShape, 1);
	
}

//create check button and background shape
function addCheckButton(){
	checkArrow = new createjs.Bitmap(imgqueue.getResult("check"));
	checkArrow.x = IMAGE_WIDTH-280;
	checkArrow.y = 40;
	checkArrow.addEventListener("click", nxtClck); /*BUG*/	// Including this following line of code will cause the images to display
															// but will prevent all event listeners from functioning.
	container.addChild(checkArrow);
	container.setChildIndex(checkArrow, 1);
	
	checkShape = new createjs.Shape();
	checkShape.graphics.beginFill("#ffffff").drawRoundRect(0, 0, 270, IMAGE_HEIGHT - 140, 30); // Original orange color: #ff8c00
	//nxtShape.graphics.beginStroke("#000000").setStrokeStyle(3).drawRoundRect(0, 0, 270, IMAGE_HEIGHT - 140, 30);
	checkShape.x = IMAGE_WIDTH - 310;
	checkShape.y = 40;
	checkShape.alpha = 0.01;
	//nxtShape.shadow = new createjs.Shadow("#000000", 5, 5, 10);
	checkShape.addEventListener("click", nxtClck);
	
	container.addChild(checkShape);
	container.setChildIndex(checkShape, 1);
}

//called when a role is picked. Remove role buttons and allow user to confirm selection or return
function roleClick(event){
	console.log("CLICK WORKED!",event.target.name);
	console.log("text: " + container.getChildIndex(title));
	container.removeChild(title);
	for(var i = 0; i < NUM_ROLES; i++){
		container.removeChild(roles[i]);
	}
	addCheckButton();
	current_role = event.target.name;
	slide = current_role + 1;
    
	createjs.Sound.stop();
	playSound(slide.toString());
	
	bmp = new createjs.Bitmap(imgqueue.getResult(slide.toString())); //Convert counter to string
	// Adding to Container
	container.addChildAt(bmp,1);
	resize();
}

//called when back button is pressed. If not already on the initial screen, return and draw role buttons
function prvClck(event){
	console.log("BACK CLICK WORKED!");
	if(slide !== 0){
		slide = 0;
		container.removeChild(bmp);
		container.removeChild(checkShape);
		container.removeChild(checkArrow);
		addRoleButtons();
		
		createjs.Sound.stop();
		playSound("0");
	
	}
	resize();
}

// Function always returns a random number between min and max (both included)
function getRndInteger(min, max) {
    return Math.floor(Math.random() * (max - min + 1) ) + min;
}

// Called to make a voiced actor speak. Plays the audio for a given type of press.
// Calls to this function should be block when audio is currently playing.
function speechClick(event){
	// Letter should hold letter as a string: i [ideaButton], c [contextButton], r [reactButton], a [aboutButton]
	var letter = event.currentTarget.myParam;	//Used to pass parameters to method
	
	var speech;		// Value to be spoken by voiced actor
	var number;		// Number to hold random value of speech options
	
	if (!playingSound) {
		// Make sure nothing is playing
		createjs.Sound.stop();
		
		// Obtain correct total for audio options of each type
		switch(letter){
			case "i":
				number = ideaAmt;
				break;
			case "c":
				number = contextAmt;
				break;
			case "r":
				number = reactAmt;
				break;
			case "a":
				number = aboutAmt;
				break;
			default:
				number = 1;
		}
		
		// Obtain a random speech option of a given type
		number = getRndInteger(1, number);	// Generate random value from [1, number]
		
		speech = letter.concat(number.toString()); // Create new string from random speech option
		playingSound = true;
		playSound(speech);
	}
	
	resize();
}

function addSpeechButtons() {
	// Create the four buttons:
	//		New ideas
	//		Stuff from the story - context
	//		Random reactions
	//		About the character
	var ideaButton;
	var contextButton;
	var reactButton;
	var aboutButton;
	var width = 600;
	var height = 500;
	var middleX = IMAGE_WIDTH / 2;
	var middleY = IMAGE_HEIGHT / 2;
	var offset = 30;
	
	// Force the taskQueue to be empty
	taskQueue = []
	
	// Adding the button for new ideas
	ideaButton = new createjs.Shape();
	ideaButton.graphics.beginFill("#DF3A01").drawRoundRect(0, 0, width, height, 30);
	ideaButton.x = middleX - width - offset;
	ideaButton.y = middleY - height - offset;
	ideaButton.addEventListener("click", speechClick)
	ideaButton.myParam = "i";
	container.addChild(ideaButton);
	container.setChildIndex(ideaButton, 1);
	
	// Adding the button for a context comment
	contextButton = new createjs.Shape();
	contextButton.graphics.beginFill("#FFFF00").drawRoundRect(0, 0, width, height, 30);
	contextButton.x = middleX + offset;
	contextButton.y = middleY - height - offset;
	contextButton.addEventListener("click", speechClick);
	contextButton.myParam = "c";
	container.addChild(contextButton);
	container.setChildIndex(contextButton, 1);
	
	// Adding the button to give reactions
	reactButton = new createjs.Shape();
	reactButton.graphics.beginFill("#4B088A").drawRoundRect(0, 0, width, height, 30);
	reactButton.x = middleX - width - offset;
	reactButton.y = middleY + offset;
	reactButton.addEventListener("click", speechClick);
	reactButton.myParam = "r";
	container.addChild(reactButton);
	container.setChildIndex(reactButton, 1);
	
	// Adding the button to tell about voiced actor
	aboutButton = new createjs.Shape();
	aboutButton.graphics.beginFill("#FF0080").drawRoundRect(0, 0, width, height, 30);
	aboutButton.x = middleX + offset;
	aboutButton.y = middleY + offset;
	aboutButton.addEventListener("click", speechClick);
	aboutButton.myParam = "a";
	container.addChild(aboutButton);
	container.setChildIndex(aboutButton, 1);
	
	resize();
}

// Called once role picking has finished to start during play component with four Buttons
function drawSpeechButtons(){
	container.removeChild(bmp);
	container.removeChild(checkShape);
	container.removeChild(checkArrow);
	container.removeChild(backArrow);
	container.removeChild(backShape);
  
	for(var j = 0; j < results.length; j++){
		container.removeChild(results[j]);
	}
	
	if(nextArrow !== undefined){
		container.removeChild(nextArrow);
		container.removeChild(nextShape);
	}
  
	if(prevArrow !== undefined){
		container.removeChild(prevArrow);
		container.removeChild(prevShape);
	}
	
	// Stop any speech currently occuring
	createjs.Sound.stop();
	playingSound = false;
	
	// Add four Buttons with connections to play random sound files
	// within certain categories.
	addSpeechButtons();
	
	resize();
}

//called when check button is pressed
function nxtClck(event){
	// Check if role picking has finished - move to four categories speech
	if (pickComplete) {
		drawSpeechButtons();
		return;
	}
	people[person].role = current_role;
	//if there are still people to pick, return to the initial screen
	if(person < people.length-1){
		person++;
		prvClck();
	}
	//if everybody has picked, draw final carousel
	else{
		pickComplete = true;
		drawResults(resultSlide);
	}
	resize();
}

function nextPage(event){
	resultSlide++;
	drawResults(resultSlide);
}

function prevPage(event){
	resultSlide--;
	drawResults(resultSlide);
}

function drawResults(page){
	container.removeChild(bmp);
	container.removeChild(checkShape);
	container.removeChild(checkArrow);
	container.removeChild(backArrow);
	container.removeChild(backShape);
  
	for(var j = 0; j < results.length; j++){
		container.removeChild(results[j]);
	}
	
	if(nextArrow !== undefined){
		container.removeChild(nextArrow);
		container.removeChild(nextShape);
	}
  
	if(prevArrow !== undefined){
		container.removeChild(prevArrow);
		container.removeChild(prevShape);
	}
	
	//draw names of users, roles underneath names
	for(var i = (page-1)*8; i < page*8; i++){
		if(people[i] !== undefined){
			console.log(people[i].name + ": " + people[i].role);
			var text = new createjs.Text(people[i].name, "80px Comic Sans MS", "#ff7700");
			text.x = (((i-(page-1)*8)%4)*450)+300;
			text.y = Math.floor((i-(page-1)*8)/4)*600+180;
			text.textBaseline = "alphabetic";
			container.addChild(text);
			
			var image = new createjs.Bitmap(imgqueue.getResult("role"+(people[i].role+1)));
			image.name = people[i].role;
			image.x = (((i-(page-1)*8)%4)*450)+200;
			image.y = Math.floor((i-(page-1)*8)/4)*600+200;
			image.addEventListener("click", taskClick);
			
			container.addChild(image);
			
			results.push(text);
			results.push(image);
		}
	}
	
	//make next arrow
	if(page*8<people.length){
		nextArrow = new createjs.Bitmap(imgqueue.getResult("right"));
		nextArrow.x = IMAGE_WIDTH-280;
		nextArrow.y = 40;
		nextArrow.addEventListener("click", nextPage);/*BUG*/	// Including this following line of code will cause the images to display
																// but will prevent all event listeners from functioning.
		container.addChild(nextArrow);
		container.setChildIndex(nextArrow, 1);
		
		nextShape = new createjs.Shape();
		nextShape.graphics.beginFill("#ffffff").drawRoundRect(0, 0, 270, IMAGE_HEIGHT - 140, 30);  // Original orange color: #ff8c00
		//prvShape.graphics.beginStroke("#000000").setStrokeStyle(3).drawRoundRect(0, 0, 270, IMAGE_HEIGHT - 140, 30);
		nextShape.x = IMAGE_WIDTH-310;
		nextShape.y = 40;
		nextShape.alpha = 0.01;
		//prvShape.shadow = new createjs.Shadow("#000000", 5, 5, 10);
		nextShape.addEventListener("click", nextPage);

		container.addChild(nextShape);
		container.setChildIndex(nextShape, 1);  
	}
	
	if(page !== 1){
		prevArrow = new createjs.Bitmap(imgqueue.getResult("left"));
		prevArrow.x = 40;
		prevArrow.y = 40;
		prevArrow.addEventListener("click", prevPage);/*BUG*/	// Including this following line of code will cause the images to display
																// but will prevent all event listeners from functioning.
		container.addChild(prevArrow);
		container.setChildIndex(prevArrow, 1);

		prevShape = new createjs.Shape();
		prevShape.graphics.beginFill("#ffffff").drawRoundRect(0, 0, 270, IMAGE_HEIGHT - 140, 30);  // Original orange color: #ff8c00
		//prvShape.graphics.beginStroke("#000000").setStrokeStyle(3).drawRoundRect(0, 0, 270, IMAGE_HEIGHT - 140, 30);
		prevShape.x = 40;
		prevShape.y = 40;
		prevShape.alpha = 0.01;
		//prvShape.shadow = new createjs.Shadow("#000000", 5, 5, 10);
		prevShape.addEventListener("click", prevPage);
  	
		container.addChild(prevShape);
		container.setChildIndex(prevShape, 1);  
	}
	
	/*BUG*/ // Back button functionality not currently added
	//addBackButton();	// From this point move to repeat initial role picking system.
	addCheckButton();	// From this point move to four types of buttons which speak
	resize();
}

// Called to remember a chosen task. Plays the audio for a given task.
// Calls to this function should be added to the queue and only played when the queue is empty
function taskClick(event){
	console.log("CLICK WORKED!",event.target.name);
	console.log("text: " + container.getChildIndex(title));

	var inQueue = false;
	
	var current_task = event.target.name;
	var tsk = current_task + 1;
    
	// Check taskQueue to see if task is currently in queue
	// Add to queue if not currently in 
	for(var i = 0; i < taskQueue.length; i++) {
		if(tsk == taskQueue[i]) {
			// Found task in queue
			inQueue = true;
		}
	}
	
	if(!inQueue){
		// Add tsk to taskQueue
		taskQueue.push(tsk);
		//alert(tsk.toString() + " added to queue")
	}
    
	
	if (!playingSound) {
		// Check to see if the queue is empty
		if(taskQueue.length > 0) {	
			createjs.Sound.stop();

			// Remove and play the first element in the queue
			tsk = taskQueue.shift();
			playingSound = true;
			playSound(tsk.toString());
		}
	}
	
	resize();
}

function playQueueSound(event) {
	//alert("Sound complete listener fires");
	playingSound = false;
	// Check to see if the queue is empty
	if(taskQueue.length > 0) {	
		//createjs.Sound.stop();	
		
		// Remove and play the first element in the queue
		tsk = taskQueue.shift();
		playingSound = true;
		playSound(tsk.toString());
	}
}

function playSound(name) {
	// Play the sound using the ID created above.
	soundInstance = createjs.Sound.play(name);
	soundInstance.addEventListener('complete', playQueueSound);
}

function loadComplete(evt) {
	// Load completed.
	//playSound("happy");
	playSound("0");
}



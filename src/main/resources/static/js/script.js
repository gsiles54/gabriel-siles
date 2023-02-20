document.querySelector('#welcomeForm').addEventListener('submit', initializeChat, true)

document.querySelector('#chat-box-input').addEventListener('submit', sendMessage, true)

var stompClient = null;
var number = null;
var nick = null;
var currentGroup=null;
var loggedUser=null;
var usersConnected= new Array();
var friends = new Array();

function initializeChat(event){
	event.preventDefault();
	number = document.querySelector('#name').value.trim();
	nick = document.querySelector('#nick').value.trim();
	if(number && nick){
		
		document.querySelector('#welcome-page').classList.add('hidden');
		document.querySelector('#chat-container').classList.remove('hidden');
	}
	const data = { number: number , nick: nick};

	
fetch('http://localhost:8080/addUser', {
	method: 'POST', 
	headers: {
	  'Content-Type': 'application/json',
	},
	body: JSON.stringify(data),
  })
	.then((response) => response.json())
	.then((data) => {
	  console.log('Success:', data);
	  currentGroup = data.groups.find((group) => group.name === 'LOBBY');
		console.log(currentGroup);
		loggedUser = data;
		friends = data.contacts;

		fetch('http://localhost:8080/getLobby')
		.then(response => response.json())
		.then((data) => {data.users.forEach(element => {
		  createNewUserOnList(element);
		 
		  fetch('http://localhost:8080/getLobbyMsg')
		.then(response => response.json())
		.then((data) => {
			console.log("aca");
			
			data.forEach(element => {
				console.log(element);
				
				onMessageReceived(element);
		 
	  });})
	  .catch(error => console.error(error));
	  });
	  connect(event);
	  
	  })
		.catch(error => console.error(error));
	})
	.catch((error) => {
	  console.error('Error:', error);
	});


console.log(currentGroup);

	
}

function connect(event) {
	
	
	if (number) {

		var socket = new SockJS('/xcaleChatApp');
		stompClient = Stomp.over(socket);

		stompClient.connect({}, connectionSuccess);
	}
	

}

function connectionSuccess() {
	stompClient.subscribe('/topic/realtimechat', onMessageReceived);

	stompClient.send("/app/chat.newUser", {}, JSON.stringify({
		sender : number,
		nick: nick,
		type : 'newUser'
	}))

}

function onMessageReceived(payload) {
	const bodyPayload = payload.body;
	var message = null;
	if(bodyPayload){
		 message = JSON.parse(payload.body);
	}else{
		message=payload;
	}
	

	var messageElement = document.createElement('li');
	console.log(messageElement);
	if (message.type === 'newUser') {
		messageElement.classList.add('event-data');
		message.text = message.sender + ' has joined the chat';
		let user = { "number":message.sender, "nick": message.nick};
		createNewUserOnList(user);
	} else if (message.type === 'Leave') {
		messageElement.classList.add('event-data');
		message.text = message.sender + ' has left the chat';
	} else {
		const notificationSound = new Audio('sounds/notification.mp3');
		messageElement.classList.add('message-data');

		var element = document.createElement('i');
		var text = document.createTextNode('');
		element.appendChild(text);
		
		messageElement.appendChild(element);

		var usernameElement = document.createElement('span');
		console.log(friends);
		console.log(message);
		const friend = friends.find(x => x.number === parseInt(message.sender,10));
			var usernameText=null;
		if(friend){
			
			usernameText= document.createTextNode(message.nick);
		}else{
			 usernameText = document.createTextNode(message.sender);
		}
		if(parseInt(message.sender,10) === loggedUser.number){
			usernameText = document.createTextNode('yo');
		}
		
		usernameElement.appendChild(usernameText);
		messageElement.appendChild(usernameElement);
		
		if (document.hidden) {
			notificationSound.play();
		}
	}

	var textElement = document.createElement('p');
	var messageText = document.createTextNode(message.text);
	textElement.appendChild(messageText);

	messageElement.appendChild(textElement);

	document.querySelector('#text-chat').appendChild(messageElement);
	document.querySelector('#text-chat').scrollTop = document
			.querySelector('#text-chat').scrollHeight;

}

function sendMessage(event) {
	var messageContent = document.querySelector('#chatInput').value.trim();
	console.log(messageContent);
	if (messageContent && stompClient) {
		var chatMessage = {
			sender : number,
			nick: nick,
			text : document.querySelector('#chatInput').value,
			group: {"id":currentGroup.id,"name":currentGroup.name},
			type : 'CHAT'
		};

		stompClient.send("/app/chat.sendMessage", {}, JSON
				.stringify(chatMessage));
		document.querySelector('#chatInput').value = '';
	}
	event.preventDefault();
}

function createNewUserOnList(element){
	let user = element.number;
	console.log(element);
	console.log(loggedUser);
	console.log(usersConnected);
	if(usersConnected.find(x => x === user) || parseInt(user,10) === loggedUser.number){
		return;
	}
	const users = document.querySelector('#groups');


	const user_el = document.createElement("div");
	user_el.classList.add("group");

	const user_el_content = document.createElement("div");
	user_el_content.classList.add("content");
	user_el_content.innerText= element.nick;

	user_el.appendChild(user_el_content);

	const user_action_el = document.createElement("div");
	user_action_el.classList.add("actions");

	const user_input_el = document.createElement("input");
	user_input_el.classList.add("text");
	user_input_el.type="text";
	user_input_el.value=user;
	user_input_el.setAttribute("readonly","readonly");

	user_el_content.appendChild(user_input_el);

	const user_add_el = document.createElement("button");
	user_add_el.classList.add("add");
	user_add_el.innerHTML = "Add contact";

	user_action_el.appendChild(user_add_el);
	user_el.appendChild(user_action_el);
	users.appendChild(user_el);


	user_add_el.addEventListener('click', () =>{
		var data = {"origin": { "number" : loggedUser.number},
					"newContact": {
						"number": user
					}};

		fetch('http://localhost:8080/addContact', {
	method: 'POST', 
	headers: {
	  'Content-Type': 'application/json',
	},
	body: JSON.stringify(data),
  })
	.then((response) => response.json())
	.then((data) => {
	  console.log('Success:', data);
		friends.push(data.contacts[0]);
		user_add_el.hidden=true;
	})
	.catch((error) => {
	  console.error('Error:', error);
	});
	})


	usersConnected.push(user);
}
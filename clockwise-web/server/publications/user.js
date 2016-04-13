import {Meteor} from 'meteor/meteor';

export default function() {
	Meteor.publish('user.login', function(login, password) {
		var responseToken = '';
		HTTP.call('POST', 'http://localhost:8080/api/authenticate', {
			data: {
				"username": login,
				"password": password
			}
		}, function(error, response) {
			if(error) {
				console.log(error);
			} else {
				console.log(response);
				responseToken = response;
			}
		});

		return responseToken;
	});
}
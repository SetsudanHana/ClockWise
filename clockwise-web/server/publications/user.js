import {Meteor} from 'meteor/meteor';

export default function() {
	Meteor.publish('user.login', function(login, password) {
		var responseToken = '';
		HTTP.call('POST', 'http://10.42.0.1:8080/api/authenticate', {
			data: {
				"username": login,
				"password": password
			}
		}, function(error, response) {
			if(error) {
				console.log("Error", error);
			} else {
				responseToken = response;
			}
		});

		return responseToken;
	});
}
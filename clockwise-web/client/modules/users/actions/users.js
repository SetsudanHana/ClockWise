import  { HTTP } from 'meteor/http';

export default {
	login({Meteor, LocalState, FlowRouter, Session}, login, password) {
    if (!login) {
      return LocalState.set('LOGIN_ERROR', 'Email is required.');
    }

    if (!password) {
      return LocalState.set('LOGIN_ERROR', 'Password is required.');
    }

    Meteor.call('user.login', login, password, (err, response) => {
      if(err) {
        return LocalState.set('LOGIN_ERROR', 'There is a problem with signing in.');
      } 

      Session.set('user_token', response);
      Session.set('username', login);

      Meteor.call('user.info', Session.get('username'), Session.get('user_token'), function(error, response) {
        if(error) {
          console.log("Error", error);
        }

        Session.set('user_info', response.data);
      });


      if(Session.get('user_token')) {
        FlowRouter.go('/dashboard');
      } else {
        return LocalState.set('LOGIN_ERROR', 'There is a problem with signing in.');
      }
    });

    LocalState.set('LOGIN_ERROR', null);
  },
  
  register({Meteor, FlowRouter, LocalState}, username, email, password, confirmPassword) {
    if(!username) {
        return LocalState.set("CREATE_USER_ERROR", "Username is required");
    }

      if (!email) {
        return LocalState.set('CREATE_USER_ERROR', 'Email is required.');
      }

      if (!password || !confirmPassword) {
        return LocalState.set('CREATE_USER_ERROR', 'Password is required.');
      }
      
      if(password !== confirmPassword) {
        return LocalState.get('CREATE_USER_ERROR', "Passwords don't match.");
      }
      
      LocalState.set('CREATE_USER_ERROR', null);

      Meteor.call('user.register', username, email, password, (err, resposne) => {
        if(err) {
          return LocalState.set('CREATE_USER_ERROR', 'There is a problem with creating an account.');
        }
      });
      if(Session.get('user_token')) {
        FlowRouter.go('/dashboard');
      } else {
        return LocalState.set('CREATE_USER_ERROR', 'There is a problem with creating an account.');
      }
  },

  clearErrors({LocalState}) {
    return LocalState.set('SAVING_ERROR', null);
  }
};
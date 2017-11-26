

            function CityEvent(){
                
                this.initFirebase();
                var topics = {};
                var cities = {};



            };

            

            CityEvent.prototype.initFirebase = function() {
              // Shortcuts to Firebase SDK features.
              this.auth = firebase.auth();
              this.database = firebase.database();
              this.storage = firebase.storage();
              // Initiates Firebase auth and listen to auth state changes.
              this.auth.onAuthStateChanged(this.onAuthStateChanged.bind(this));
            };

            CityEvent.prototype.checkSignedInWithMessage = function() {
                // Return true if the user is signed in Firebase
                if (this.auth.currentUser) {
                  return true;
                }

                

                // Display a message to the user using a Toast.
                var data = {
                  message: 'You must sign-in first',
                  timeout: 2000
                };
                //this.signInSnackbar.MaterialSnackbar.showSnackbar(data);
                //alert('you must sign-in first');
                return false;
            };


            CityEvent.prototype.signIn = function() {
                  // Sign in Firebase using popup auth and Google as the identity provider.
                      var provider = new firebase.auth.GoogleAuthProvider();
                      this.auth.signInWithRedirect(provider);
                      return this.checkSignedInWithMessage();
            };

            CityEvent.prototype.signOut = function(){
                if(this.checkSignedInWithMessage()){
                    this.auth.signOut();
                }
            };

            CityEvent.prototype.guest = function(){
                alert('guest');
            };

            CityEvent.prototype.onAuthStateChanged = function(user){
                if(user){
                    this.signOutButton.removeAttribute('hidden');
                    this.signInButton.setAttribute('hidden','true');
                } else{
                    this.signOutButton.setAttribute('hidden','true');
                    this.signInButton.removeAttribute('hidden');

                }

            };
            
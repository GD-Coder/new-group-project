import 'app/styles/home.styles'
import templateUrl from 'app/html/home.template'

/*changes made by carol
*/
const controller = class FtRegisterController {
  constructor ($log, $state, $location, $http, ftGameSettings, $window, $rootScope, appService, localStorageService) {
    'ngInject'
    this.localStorageService = localStorageService
    this.service = appService
    this.settings = ftGameSettings
    this.$location = $location
    this.$window = $window
    this.$state = $state
    this.$http = $http
    this.loadTweets()
    $rootScope.$on(this.$location.$routeChangeStart, this.checkAuth())
    $log.log('ft-home is a go')
  }
  checkAuth () {
    let isauthenticated = this.localStorageService.get('isAuthenticated')
    if(!isauthenticated) {
      this.$state.transitionTo('login')
    }
  }
  loadTweets () {
    let username = this.localStorageService.get('username')
    let password = this.localStorageService.get('password')

    this.$http({
    method: 'GET',
    url: 'http://localhost:8080/user/users/@' + username + '/feed'
  }).then(this.successCallback = (response) => {
     this.tweets = []
     this.tweets = response.data
    // alert(JSON.stringify(this.tweets))
  }, this.errorCallback = (response) => {
    this.error = 'Username or password are incorrect, please try again.'
  })
  }
likeTweet (id) {

  let username = this.localStorageService.get('username')
  let password = this.localStorageService.get('password')
  //alert(username + ' ' + password)
  this.$http({
  method: 'POST',
  url:  'http://localhost:8080/tweet/tweets/' + id + '/like',
  data: {credentials: { password: password, username: username }}
}).then(this.successCallback = (response) => {
   alert('Liked!!')
}, this.errorCallback = (response) => {
  this.error = 'Username or password are incorrect, please try again.'
})
}
repostTweet (id) {
 //  alert('Repost!')
 let username = this.localStorageService.get('username')
 let password = this.localStorageService.get('password')
  this.$http({
  method: 'POST',
  url: 'http://localhost:8080/tweet/tweets/' + id + '/repost',
  data: {credentials: { password: password, username: username }}
}).then(this.successCallback = (response) => {
  //  alert('201')
}, this.errorCallback = (response) => {
  this.error = 'Username or password are incorrect, please try again.'
})
this.$window.location.reload()
}
postTweet () {
  // alert('Post!')
  let username = this.localStorageService.get('username')
  let password = this.localStorageService.get('password')
  this.$http({
  method: 'POST',
  url: 'http://localhost:8080/tweet/tweets',
  data: {'content': this.tweet, 'credentials': { 'password': password, 'username': username }}
}).then(this.successCallback = (response) => {
  //  alert('201')
this.loadTweets()

}, this.errorCallback = (response) => {
  this.error = 'Username or password are incorrect, please try again.'
})
}
replyTweet (id) {
  //alert(this.reply)
  let username = this.localStorageService.get('username')
  let password = this.localStorageService.get('password')
  this.$http({
  method: 'POST',
  url: 'http://localhost:8080/tweet/tweets/' + id + '/reply',
  data: {'content': this.reply, 'credentials': { 'password': password, 'username': username }}
}).then(this.successCallback = (response) => {
  //  alert('201')
}, this.errorCallback = (response) => {
  this.error = 'Username or password are incorrect, please try again.'
})
this.$window.location.reload()
}
deleteTweet (id) {
 //  alert('Repost!')
 let username = this.localStorageService.get('username')
 let password = this.localStorageService.get('password')
  this.$http({
  method: 'POST',
  url: 'http://localhost:8080/tweet/tweets/' + id,
  data: {credentials: { password: password, username: username }}
}).then(this.successCallback = (response) => {
//  alert("reloading")
}, this.errorCallback = (response) => {
  this.error = 'Username or password are incorrect, please try again.'
})
this.$window.location.reload()
}
getSelUser (author) {
  this.service.saveState('author', author)
  this.$state.transitionTo('profile')
}
logout () {
  this.localStorageService.remove('username')
  this.localStorageService.remove('password')
  this.localStorageService.remove('isAuthenticated')
  this.localStorageService.remove('author')
  this.$state.transitionTo('login')
}
}
export const ftHome = {
  controller,
  templateUrl,
  controllerAs: 'home'
}

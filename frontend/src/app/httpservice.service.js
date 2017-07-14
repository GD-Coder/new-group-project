export const HttpService = class {
  constructor ($http) {
    'ngInject'
    this.$http = $http
  }

  getUserFollowing (username) {
    return this.$http.get(`http://localhost:8080/user/users/@${username}/following`)
    .then((response) => {
      return response.data
    }, (error) => {
      console.log('failed getUserFollowing', error.data)
    })
  }

  getUserFollowers (username) {
    return this.$http.get(`http://localhost:8080/user/users/@${username}/followers`)
    .then((response) => {
      return response.data
    }, (error) => {
      console.log('failed getUserFollowers', error.data)
    })
  }

  getAllUsers () {
    return this.$http.get('http://localhost:8080/user/users')
    .then((response) => {
      return response.data
    }, (error) => {
      console.log('failed getAllUsers', error.data)
    })
  }

  followUser (credentials, userFollowed) {
    return this.$http.post(`http://localhost:8080/user/users/@${userFollowed}/follow`, credentials)
    .then((response) => {
      console.log('success followUser', response.data)
      return response.data
    }, (error) => {
      console.log('failed followUser', error.data)
    })
  }

  unFollowUser (credentials, userFollowed) {
    return this.$http.post(`http://localhost:8080/user/users/@${userFollowed}/unfollow`, credentials)
    .then((response) => {
      console.log('success unFollowUser', response.data)
      return response.data
    }, (error) => {
      console.log('failed unFollowUser', error.data)
    })
  }

}

export const profileConfig =
  ($stateProvider) => {
    'ngInject'
    $stateProvider.state({
      name: 'profile',
      url: '/profile',
      component: 'ftProfile'
    })
  }

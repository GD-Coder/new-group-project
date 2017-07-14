export const loginConfig =
  ($stateProvider) => {
    'ngInject'
    $stateProvider.state({
      name: 'login',
      url: '/login',
      component: 'ftLogin'
    })
  }

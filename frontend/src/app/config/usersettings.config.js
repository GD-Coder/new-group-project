export const usersettingsConfig =
  ($stateProvider) => {
    'ngInject'
    $stateProvider.state({
      name: 'usersettings',
      url: '/usersettings',
      component: 'ftUserSettings'
    })
  }

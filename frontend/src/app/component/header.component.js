import 'app/styles/header.styles'
import templateUrl from 'app/html/header.template'

const controller =
  class FtHeaderController {
    constructor ($log, appService, ftGameSettings) {
      'ngInject'
      this.settings = ftGameSettings
      this.service = appService
      $log.log('ft-header is a go')
    }
    get total () {
      return this.service.total
    }
    set total (val) {
      this.service.total = val
    }
    updateTotal () {
      return this.service.points
    }
    getAuth () {
      return this.settings.userInfo.isAuthenticated
    }
 }
export const ftHeader = {
  controller,
  templateUrl,
  controllerAs: 'header'
}

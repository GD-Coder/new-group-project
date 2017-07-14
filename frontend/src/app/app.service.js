export class AppService {
  constructor ($interval, localStorageService, $timeout, ftGameSettings) {
    'ngInject'
    this.$interval = $interval
    this.localStorageService = localStorageService
    this.$timeout = $timeout
    this.settings = ftGameSettings
    this.$timeout(() => {
      this.points = this.localStorageService.get('points')
      this.mod = this.localStorageService.get('modifier')
      this.autom = this.localStorageService.get('automod')
      // this.user = this.localStorageService.get('isAuthenticated')
      if (this.points > 0) {
        this.settings.defaultGameSettings.total = this.points
      }
      if (this.mod > 0) {
        this.settings.defaultPointSettings.increment += this.mod
        this.settings.defaultPointSettings.modifier.amount = this.mod
        this.settings.defaultGameSettings.modifier = this.mod
        if (this.autom > 0) {
          this.settings.defaultGameSettings.autoclickers = this.autom
        }
      }
    }, 10)
    for (let i = 0; i < this.autom; i++) { this.auto() }
      // alert(this.points)
      // alert(this.mod)
      // alert(this.autom)
      // alert(this.user)
  }
  increment () {
    this.settings.defaultGameSettings.total += this.settings.defaultPointSettings.increment
  }
  multiple () {
    this.settings.defaultPointSettings.increment += this.settings.defaultPointSettings.modifier.amount
    this.settings.defaultGameSettings.total += this.settings.defaultPointSettings.increment * this.settings.defaultPointSettings.modifier.amount
    this.settings.defaultPointSettings.modifier.amount += 0.2
    this.decrementMulti()
  }
  decrementMulti () {
    this.settings.defaultGameSettings.total -= this.settings.defaultPointSettings.modifier.cost
  }
  decrementAuto () {
    this.settings.defaultGameSettings.total -= this.settings.defaultPointSettings.autoclicker.cost
  }
  saveState (key, val) {
    this.localStorageService.set(key, val)
  }
  get points () {
    return this.settings.defaultGameSettings.total
  }
  set points (val) {
    this.settings.defaultGameSettings.total = val
  }
  auto () {
    this.$interval(() => {
      this.settings.defaultGameSettings.total++
      this.saveState('automod', this.settings.defaultGameSettings.autoclickers)
      this.saveState('points', this.settings.defaultGameSettings.total)
    }, 1000)
  }
}

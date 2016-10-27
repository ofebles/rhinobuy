(function() {
    'use strict';

    angular
        .module('rhinobuyApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('casa', {
            parent: 'entity',
            url: '/casa',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'rhinobuyApp.casa.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/casa/casas.html',
                    controller: 'CasaController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('casa');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('casa-detail', {
            parent: 'entity',
            url: '/casa/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'rhinobuyApp.casa.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/casa/casa-detail.html',
                    controller: 'CasaDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('casa');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Casa', function($stateParams, Casa) {
                    return Casa.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'casa',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('casa-detail.edit', {
            parent: 'casa-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/casa/casa-dialog.html',
                    controller: 'CasaDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Casa', function(Casa) {
                            return Casa.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('casa.new', {
            parent: 'casa',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/casa/casa-dialog.html',
                    controller: 'CasaDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                direccion: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('casa', null, { reload: 'casa' });
                }, function() {
                    $state.go('casa');
                });
            }]
        })
        .state('casa.edit', {
            parent: 'casa',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/casa/casa-dialog.html',
                    controller: 'CasaDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Casa', function(Casa) {
                            return Casa.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('casa', null, { reload: 'casa' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('casa.delete', {
            parent: 'casa',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/casa/casa-delete-dialog.html',
                    controller: 'CasaDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Casa', function(Casa) {
                            return Casa.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('casa', null, { reload: 'casa' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();

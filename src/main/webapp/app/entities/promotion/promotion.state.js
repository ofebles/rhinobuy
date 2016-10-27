(function() {
    'use strict';

    angular
        .module('rhinobuyApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('promotion', {
            parent: 'entity',
            url: '/promotion',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'rhinobuyApp.promotion.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/promotion/promotions.html',
                    controller: 'PromotionController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('promotion');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('promotion-detail', {
            parent: 'entity',
            url: '/promotion/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'rhinobuyApp.promotion.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/promotion/promotion-detail.html',
                    controller: 'PromotionDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('promotion');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Promotion', function($stateParams, Promotion) {
                    return Promotion.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'promotion',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('promotion-detail.edit', {
            parent: 'promotion-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/promotion/promotion-dialog.html',
                    controller: 'PromotionDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Promotion', function(Promotion) {
                            return Promotion.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('promotion.new', {
            parent: 'promotion',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/promotion/promotion-dialog.html',
                    controller: 'PromotionDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                description: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('promotion', null, { reload: 'promotion' });
                }, function() {
                    $state.go('promotion');
                });
            }]
        })
        .state('promotion.edit', {
            parent: 'promotion',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/promotion/promotion-dialog.html',
                    controller: 'PromotionDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Promotion', function(Promotion) {
                            return Promotion.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('promotion', null, { reload: 'promotion' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('promotion.delete', {
            parent: 'promotion',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/promotion/promotion-delete-dialog.html',
                    controller: 'PromotionDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Promotion', function(Promotion) {
                            return Promotion.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('promotion', null, { reload: 'promotion' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();

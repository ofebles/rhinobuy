(function() {
    'use strict';

    angular
        .module('rhinobuyApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('shoping-cart', {
            parent: 'entity',
            url: '/shoping-cart',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'rhinobuyApp.shopingCart.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/shoping-cart/shoping-carts.html',
                    controller: 'ShopingCartController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('shopingCart');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('shoping-cart-detail', {
            parent: 'entity',
            url: '/shoping-cart/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'rhinobuyApp.shopingCart.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/shoping-cart/shoping-cart-detail.html',
                    controller: 'ShopingCartDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('shopingCart');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'ShopingCart', function($stateParams, ShopingCart) {
                    return ShopingCart.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'shoping-cart',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('shoping-cart-detail.edit', {
            parent: 'shoping-cart-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/shoping-cart/shoping-cart-dialog.html',
                    controller: 'ShopingCartDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['ShopingCart', function(ShopingCart) {
                            return ShopingCart.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('shoping-cart.new', {
            parent: 'shoping-cart',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/shoping-cart/shoping-cart-dialog.html',
                    controller: 'ShopingCartDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                date: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('shoping-cart', null, { reload: 'shoping-cart' });
                }, function() {
                    $state.go('shoping-cart');
                });
            }]
        })
        .state('shoping-cart.edit', {
            parent: 'shoping-cart',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/shoping-cart/shoping-cart-dialog.html',
                    controller: 'ShopingCartDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['ShopingCart', function(ShopingCart) {
                            return ShopingCart.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('shoping-cart', null, { reload: 'shoping-cart' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('shoping-cart.delete', {
            parent: 'shoping-cart',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/shoping-cart/shoping-cart-delete-dialog.html',
                    controller: 'ShopingCartDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['ShopingCart', function(ShopingCart) {
                            return ShopingCart.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('shoping-cart', null, { reload: 'shoping-cart' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();

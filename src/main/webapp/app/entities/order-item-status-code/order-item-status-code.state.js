(function() {
    'use strict';

    angular
        .module('rhinobuyApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('order-item-status-code', {
            parent: 'entity',
            url: '/order-item-status-code',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'rhinobuyApp.orderItemStatusCode.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/order-item-status-code/order-item-status-codes.html',
                    controller: 'OrderItemStatusCodeController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('orderItemStatusCode');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('order-item-status-code-detail', {
            parent: 'entity',
            url: '/order-item-status-code/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'rhinobuyApp.orderItemStatusCode.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/order-item-status-code/order-item-status-code-detail.html',
                    controller: 'OrderItemStatusCodeDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('orderItemStatusCode');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'OrderItemStatusCode', function($stateParams, OrderItemStatusCode) {
                    return OrderItemStatusCode.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'order-item-status-code',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('order-item-status-code-detail.edit', {
            parent: 'order-item-status-code-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/order-item-status-code/order-item-status-code-dialog.html',
                    controller: 'OrderItemStatusCodeDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['OrderItemStatusCode', function(OrderItemStatusCode) {
                            return OrderItemStatusCode.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('order-item-status-code.new', {
            parent: 'order-item-status-code',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/order-item-status-code/order-item-status-code-dialog.html',
                    controller: 'OrderItemStatusCodeDialogController',
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
                    $state.go('order-item-status-code', null, { reload: 'order-item-status-code' });
                }, function() {
                    $state.go('order-item-status-code');
                });
            }]
        })
        .state('order-item-status-code.edit', {
            parent: 'order-item-status-code',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/order-item-status-code/order-item-status-code-dialog.html',
                    controller: 'OrderItemStatusCodeDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['OrderItemStatusCode', function(OrderItemStatusCode) {
                            return OrderItemStatusCode.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('order-item-status-code', null, { reload: 'order-item-status-code' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('order-item-status-code.delete', {
            parent: 'order-item-status-code',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/order-item-status-code/order-item-status-code-delete-dialog.html',
                    controller: 'OrderItemStatusCodeDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['OrderItemStatusCode', function(OrderItemStatusCode) {
                            return OrderItemStatusCode.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('order-item-status-code', null, { reload: 'order-item-status-code' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();

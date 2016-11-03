(function() {
    'use strict';

    angular
        .module('rhinobuyApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('customer-payment-method', {
            parent: 'entity',
            url: '/customer-payment-method',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'rhinobuyApp.customerPaymentMethod.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/customer-payment-method/customer-payment-methods.html',
                    controller: 'CustomerPaymentMethodController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('customerPaymentMethod');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('customer-payment-method-detail', {
            parent: 'entity',
            url: '/customer-payment-method/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'rhinobuyApp.customerPaymentMethod.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/customer-payment-method/customer-payment-method-detail.html',
                    controller: 'CustomerPaymentMethodDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('customerPaymentMethod');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'CustomerPaymentMethod', function($stateParams, CustomerPaymentMethod) {
                    return CustomerPaymentMethod.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'customer-payment-method',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('customer-payment-method-detail.edit', {
            parent: 'customer-payment-method-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/customer-payment-method/customer-payment-method-dialog.html',
                    controller: 'CustomerPaymentMethodDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['CustomerPaymentMethod', function(CustomerPaymentMethod) {
                            return CustomerPaymentMethod.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('customer-payment-method.new', {
            parent: 'customer-payment-method',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/customer-payment-method/customer-payment-method-dialog.html',
                    controller: 'CustomerPaymentMethodDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                code: null,
                                creditCardNumber: null,
                                details: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('customer-payment-method', null, { reload: 'customer-payment-method' });
                }, function() {
                    $state.go('customer-payment-method');
                });
            }]
        })
        .state('customer-payment-method.edit', {
            parent: 'customer-payment-method',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/customer-payment-method/customer-payment-method-dialog.html',
                    controller: 'CustomerPaymentMethodDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['CustomerPaymentMethod', function(CustomerPaymentMethod) {
                            return CustomerPaymentMethod.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('customer-payment-method', null, { reload: 'customer-payment-method' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('customer-payment-method.delete', {
            parent: 'customer-payment-method',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/customer-payment-method/customer-payment-method-delete-dialog.html',
                    controller: 'CustomerPaymentMethodDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['CustomerPaymentMethod', function(CustomerPaymentMethod) {
                            return CustomerPaymentMethod.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('customer-payment-method', null, { reload: 'customer-payment-method' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();

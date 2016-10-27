(function() {
    'use strict';

    angular
        .module('rhinobuyApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('reference-payment-method', {
            parent: 'entity',
            url: '/reference-payment-method',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'rhinobuyApp.referencePaymentMethod.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/reference-payment-method/reference-payment-methods.html',
                    controller: 'ReferencePaymentMethodController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('referencePaymentMethod');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('reference-payment-method-detail', {
            parent: 'entity',
            url: '/reference-payment-method/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'rhinobuyApp.referencePaymentMethod.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/reference-payment-method/reference-payment-method-detail.html',
                    controller: 'ReferencePaymentMethodDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('referencePaymentMethod');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'ReferencePaymentMethod', function($stateParams, ReferencePaymentMethod) {
                    return ReferencePaymentMethod.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'reference-payment-method',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('reference-payment-method-detail.edit', {
            parent: 'reference-payment-method-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/reference-payment-method/reference-payment-method-dialog.html',
                    controller: 'ReferencePaymentMethodDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['ReferencePaymentMethod', function(ReferencePaymentMethod) {
                            return ReferencePaymentMethod.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('reference-payment-method.new', {
            parent: 'reference-payment-method',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/reference-payment-method/reference-payment-method-dialog.html',
                    controller: 'ReferencePaymentMethodDialogController',
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
                    $state.go('reference-payment-method', null, { reload: 'reference-payment-method' });
                }, function() {
                    $state.go('reference-payment-method');
                });
            }]
        })
        .state('reference-payment-method.edit', {
            parent: 'reference-payment-method',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/reference-payment-method/reference-payment-method-dialog.html',
                    controller: 'ReferencePaymentMethodDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['ReferencePaymentMethod', function(ReferencePaymentMethod) {
                            return ReferencePaymentMethod.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('reference-payment-method', null, { reload: 'reference-payment-method' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('reference-payment-method.delete', {
            parent: 'reference-payment-method',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/reference-payment-method/reference-payment-method-delete-dialog.html',
                    controller: 'ReferencePaymentMethodDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['ReferencePaymentMethod', function(ReferencePaymentMethod) {
                            return ReferencePaymentMethod.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('reference-payment-method', null, { reload: 'reference-payment-method' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();

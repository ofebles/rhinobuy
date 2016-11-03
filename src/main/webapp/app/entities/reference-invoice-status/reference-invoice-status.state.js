(function() {
    'use strict';

    angular
        .module('rhinobuyApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('reference-invoice-status', {
            parent: 'entity',
            url: '/reference-invoice-status',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'rhinobuyApp.referenceInvoiceStatus.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/reference-invoice-status/reference-invoice-statuses.html',
                    controller: 'ReferenceInvoiceStatusController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('referenceInvoiceStatus');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('reference-invoice-status-detail', {
            parent: 'entity',
            url: '/reference-invoice-status/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'rhinobuyApp.referenceInvoiceStatus.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/reference-invoice-status/reference-invoice-status-detail.html',
                    controller: 'ReferenceInvoiceStatusDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('referenceInvoiceStatus');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'ReferenceInvoiceStatus', function($stateParams, ReferenceInvoiceStatus) {
                    return ReferenceInvoiceStatus.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'reference-invoice-status',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('reference-invoice-status-detail.edit', {
            parent: 'reference-invoice-status-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/reference-invoice-status/reference-invoice-status-dialog.html',
                    controller: 'ReferenceInvoiceStatusDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['ReferenceInvoiceStatus', function(ReferenceInvoiceStatus) {
                            return ReferenceInvoiceStatus.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('reference-invoice-status.new', {
            parent: 'reference-invoice-status',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/reference-invoice-status/reference-invoice-status-dialog.html',
                    controller: 'ReferenceInvoiceStatusDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                number: null,
                                invoiceDate: null,
                                invoiceDetails: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('reference-invoice-status', null, { reload: 'reference-invoice-status' });
                }, function() {
                    $state.go('reference-invoice-status');
                });
            }]
        })
        .state('reference-invoice-status.edit', {
            parent: 'reference-invoice-status',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/reference-invoice-status/reference-invoice-status-dialog.html',
                    controller: 'ReferenceInvoiceStatusDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['ReferenceInvoiceStatus', function(ReferenceInvoiceStatus) {
                            return ReferenceInvoiceStatus.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('reference-invoice-status', null, { reload: 'reference-invoice-status' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('reference-invoice-status.delete', {
            parent: 'reference-invoice-status',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/reference-invoice-status/reference-invoice-status-delete-dialog.html',
                    controller: 'ReferenceInvoiceStatusDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['ReferenceInvoiceStatus', function(ReferenceInvoiceStatus) {
                            return ReferenceInvoiceStatus.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('reference-invoice-status', null, { reload: 'reference-invoice-status' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();

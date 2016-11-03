(function() {
    'use strict';

    angular
        .module('rhinobuyApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('supplier', {
            parent: 'entity',
            url: '/supplier',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'rhinobuyApp.supplier.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/supplier/suppliers.html',
                    controller: 'SupplierController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('supplier');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('supplier-detail', {
            parent: 'entity',
            url: '/supplier/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'rhinobuyApp.supplier.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/supplier/supplier-detail.html',
                    controller: 'SupplierDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('supplier');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Supplier', function($stateParams, Supplier) {
                    return Supplier.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'supplier',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('supplier-detail.edit', {
            parent: 'supplier-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/supplier/supplier-dialog.html',
                    controller: 'SupplierDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Supplier', function(Supplier) {
                            return Supplier.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('supplier.new', {
            parent: 'supplier',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/supplier/supplier-dialog.html',
                    controller: 'SupplierDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                name: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('supplier', null, { reload: 'supplier' });
                }, function() {
                    $state.go('supplier');
                });
            }]
        })
        .state('supplier.edit', {
            parent: 'supplier',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/supplier/supplier-dialog.html',
                    controller: 'SupplierDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Supplier', function(Supplier) {
                            return Supplier.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('supplier', null, { reload: 'supplier' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('supplier.delete', {
            parent: 'supplier',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/supplier/supplier-delete-dialog.html',
                    controller: 'SupplierDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Supplier', function(Supplier) {
                            return Supplier.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('supplier', null, { reload: 'supplier' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();

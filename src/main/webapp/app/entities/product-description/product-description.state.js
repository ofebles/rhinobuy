(function() {
    'use strict';

    angular
        .module('rhinobuyApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('product-description', {
            parent: 'entity',
            url: '/product-description',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'rhinobuyApp.productDescription.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/product-description/product-descriptions.html',
                    controller: 'ProductDescriptionController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('productDescription');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('product-description-detail', {
            parent: 'entity',
            url: '/product-description/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'rhinobuyApp.productDescription.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/product-description/product-description-detail.html',
                    controller: 'ProductDescriptionDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('productDescription');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'ProductDescription', function($stateParams, ProductDescription) {
                    return ProductDescription.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'product-description',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('product-description-detail.edit', {
            parent: 'product-description-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/product-description/product-description-dialog.html',
                    controller: 'ProductDescriptionDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['ProductDescription', function(ProductDescription) {
                            return ProductDescription.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('product-description.new', {
            parent: 'product-description',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/product-description/product-description-dialog.html',
                    controller: 'ProductDescriptionDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                content: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('product-description', null, { reload: 'product-description' });
                }, function() {
                    $state.go('product-description');
                });
            }]
        })
        .state('product-description.edit', {
            parent: 'product-description',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/product-description/product-description-dialog.html',
                    controller: 'ProductDescriptionDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['ProductDescription', function(ProductDescription) {
                            return ProductDescription.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('product-description', null, { reload: 'product-description' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('product-description.delete', {
            parent: 'product-description',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/product-description/product-description-delete-dialog.html',
                    controller: 'ProductDescriptionDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['ProductDescription', function(ProductDescription) {
                            return ProductDescription.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('product-description', null, { reload: 'product-description' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();

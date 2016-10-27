(function() {
    'use strict';

    angular
        .module('rhinobuyApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('reference-language', {
            parent: 'entity',
            url: '/reference-language',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'rhinobuyApp.referenceLanguage.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/reference-language/reference-languages.html',
                    controller: 'ReferenceLanguageController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('referenceLanguage');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('reference-language-detail', {
            parent: 'entity',
            url: '/reference-language/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'rhinobuyApp.referenceLanguage.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/reference-language/reference-language-detail.html',
                    controller: 'ReferenceLanguageDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('referenceLanguage');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'ReferenceLanguage', function($stateParams, ReferenceLanguage) {
                    return ReferenceLanguage.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'reference-language',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('reference-language-detail.edit', {
            parent: 'reference-language-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/reference-language/reference-language-dialog.html',
                    controller: 'ReferenceLanguageDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['ReferenceLanguage', function(ReferenceLanguage) {
                            return ReferenceLanguage.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('reference-language.new', {
            parent: 'reference-language',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/reference-language/reference-language-dialog.html',
                    controller: 'ReferenceLanguageDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                lang: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('reference-language', null, { reload: 'reference-language' });
                }, function() {
                    $state.go('reference-language');
                });
            }]
        })
        .state('reference-language.edit', {
            parent: 'reference-language',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/reference-language/reference-language-dialog.html',
                    controller: 'ReferenceLanguageDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['ReferenceLanguage', function(ReferenceLanguage) {
                            return ReferenceLanguage.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('reference-language', null, { reload: 'reference-language' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('reference-language.delete', {
            parent: 'reference-language',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/reference-language/reference-language-delete-dialog.html',
                    controller: 'ReferenceLanguageDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['ReferenceLanguage', function(ReferenceLanguage) {
                            return ReferenceLanguage.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('reference-language', null, { reload: 'reference-language' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();

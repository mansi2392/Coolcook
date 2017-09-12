(function() {
    'use strict';

    angular
        .module('coolcookApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('category-my-suffix', {
            parent: 'entity',
            url: '/category-my-suffix',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'coolcookApp.category.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/category/categoriesmySuffix.html',
                    controller: 'CategoryMySuffixController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('category');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('category-my-suffix-detail', {
            parent: 'category-my-suffix',
            url: '/category-my-suffix/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'coolcookApp.category.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/category/category-my-suffix-detail.html',
                    controller: 'CategoryMySuffixDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('category');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Category', function($stateParams, Category) {
                    return Category.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'category-my-suffix',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('category-my-suffix-detail.edit', {
            parent: 'category-my-suffix-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/category/category-my-suffix-dialog.html',
                    controller: 'CategoryMySuffixDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Category', function(Category) {
                            return Category.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('category-my-suffix.new', {
            parent: 'category-my-suffix',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/category/category-my-suffix-dialog.html',
                    controller: 'CategoryMySuffixDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                categoryName: null,
                                type: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('category-my-suffix', null, { reload: 'category-my-suffix' });
                }, function() {
                    $state.go('category-my-suffix');
                });
            }]
        })
        .state('category-my-suffix.edit', {
            parent: 'category-my-suffix',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/category/category-my-suffix-dialog.html',
                    controller: 'CategoryMySuffixDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Category', function(Category) {
                            return Category.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('category-my-suffix', null, { reload: 'category-my-suffix' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('category-my-suffix.delete', {
            parent: 'category-my-suffix',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/category/category-my-suffix-delete-dialog.html',
                    controller: 'CategoryMySuffixDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Category', function(Category) {
                            return Category.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('category-my-suffix', null, { reload: 'category-my-suffix' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();

(function() {
    'use strict';

    angular
        .module('coolcookApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('ingredient-at-home-my-suffix', {
            parent: 'entity',
            url: '/ingredient-at-home-my-suffix?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'coolcookApp.ingredientAtHome.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/ingredient-at-home/ingredient-at-homesmySuffix.html',
                    controller: 'IngredientAtHomeMySuffixController',
                    controllerAs: 'vm'
                }
            },
            params: {
                page: {
                    value: '1',
                    squash: true
                },
                sort: {
                    value: 'id,asc',
                    squash: true
                },
                search: null
            },
            resolve: {
                pagingParams: ['$stateParams', 'PaginationUtil', function ($stateParams, PaginationUtil) {
                    return {
                        page: PaginationUtil.parsePage($stateParams.page),
                        sort: $stateParams.sort,
                        predicate: PaginationUtil.parsePredicate($stateParams.sort),
                        ascending: PaginationUtil.parseAscending($stateParams.sort),
                        search: $stateParams.search
                    };
                }],
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('ingredientAtHome');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('ingredient-at-home-my-suffix-detail', {
            parent: 'ingredient-at-home-my-suffix',
            url: '/ingredient-at-home-my-suffix/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'coolcookApp.ingredientAtHome.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/ingredient-at-home/ingredient-at-home-my-suffix-detail.html',
                    controller: 'IngredientAtHomeMySuffixDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('ingredientAtHome');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'IngredientAtHome', function($stateParams, IngredientAtHome) {
                    return IngredientAtHome.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'ingredient-at-home-my-suffix',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('ingredient-at-home-my-suffix-detail.edit', {
            parent: 'ingredient-at-home-my-suffix-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/ingredient-at-home/ingredient-at-home-my-suffix-dialog.html',
                    controller: 'IngredientAtHomeMySuffixDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['IngredientAtHome', function(IngredientAtHome) {
                            return IngredientAtHome.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('ingredient-at-home-my-suffix.new', {
            parent: 'ingredient-at-home-my-suffix',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/ingredient-at-home/ingredient-at-home-my-suffix-dialog.html',
                    controller: 'IngredientAtHomeMySuffixDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                createdAt: null,
                                updatedAt: null,
                                expiryDate: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('ingredient-at-home-my-suffix', null, { reload: 'ingredient-at-home-my-suffix' });
                }, function() {
                    $state.go('ingredient-at-home-my-suffix');
                });
            }]
        })
        .state('ingredient-at-home-my-suffix.edit', {
            parent: 'ingredient-at-home-my-suffix',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/ingredient-at-home/ingredient-at-home-my-suffix-dialog.html',
                    controller: 'IngredientAtHomeMySuffixDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['IngredientAtHome', function(IngredientAtHome) {
                            return IngredientAtHome.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('ingredient-at-home-my-suffix', null, { reload: 'ingredient-at-home-my-suffix' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('ingredient-at-home-my-suffix.delete', {
            parent: 'ingredient-at-home-my-suffix',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/ingredient-at-home/ingredient-at-home-my-suffix-delete-dialog.html',
                    controller: 'IngredientAtHomeMySuffixDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['IngredientAtHome', function(IngredientAtHome) {
                            return IngredientAtHome.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('ingredient-at-home-my-suffix', null, { reload: 'ingredient-at-home-my-suffix' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();

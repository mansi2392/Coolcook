(function() {
    'use strict';

    angular
        .module('coolcookApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('ingredient-master-my-suffix', {
            parent: 'entity',
            url: '/ingredient-master-my-suffix?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'coolcookApp.ingredientMaster.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/ingredient-master/ingredient-mastersmySuffix.html',
                    controller: 'IngredientMasterMySuffixController',
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
                    $translatePartialLoader.addPart('ingredientMaster');
                    $translatePartialLoader.addPart('unitOfQuantity');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('ingredient-master-my-suffix-detail', {
            parent: 'ingredient-master-my-suffix',
            url: '/ingredient-master-my-suffix/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'coolcookApp.ingredientMaster.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/ingredient-master/ingredient-master-my-suffix-detail.html',
                    controller: 'IngredientMasterMySuffixDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('ingredientMaster');
                    $translatePartialLoader.addPart('unitOfQuantity');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'IngredientMaster', function($stateParams, IngredientMaster) {
                    return IngredientMaster.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'ingredient-master-my-suffix',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('ingredient-master-my-suffix-detail.edit', {
            parent: 'ingredient-master-my-suffix-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/ingredient-master/ingredient-master-my-suffix-dialog.html',
                    controller: 'IngredientMasterMySuffixDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['IngredientMaster', function(IngredientMaster) {
                            return IngredientMaster.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('ingredient-master-my-suffix.new', {
            parent: 'ingredient-master-my-suffix',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/ingredient-master/ingredient-master-my-suffix-dialog.html',
                    controller: 'IngredientMasterMySuffixDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                name: null,
                                image: null,
                                imageContentType: null,
                                unit: null,
                                defaultQty: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('ingredient-master-my-suffix', null, { reload: 'ingredient-master-my-suffix' });
                }, function() {
                    $state.go('ingredient-master-my-suffix');
                });
            }]
        })
        .state('ingredient-master-my-suffix.edit', {
            parent: 'ingredient-master-my-suffix',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/ingredient-master/ingredient-master-my-suffix-dialog.html',
                    controller: 'IngredientMasterMySuffixDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['IngredientMaster', function(IngredientMaster) {
                            return IngredientMaster.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('ingredient-master-my-suffix', null, { reload: 'ingredient-master-my-suffix' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('ingredient-master-my-suffix.delete', {
            parent: 'ingredient-master-my-suffix',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/ingredient-master/ingredient-master-my-suffix-delete-dialog.html',
                    controller: 'IngredientMasterMySuffixDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['IngredientMaster', function(IngredientMaster) {
                            return IngredientMaster.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('ingredient-master-my-suffix', null, { reload: 'ingredient-master-my-suffix' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();

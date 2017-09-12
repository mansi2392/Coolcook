(function() {
    'use strict';

    angular
        .module('coolcookApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('recipe-master-my-suffix', {
            parent: 'entity',
            url: '/recipe-master-my-suffix?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'coolcookApp.recipeMaster.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/recipe-master/recipe-mastersmySuffix.html',
                    controller: 'RecipeMasterMySuffixController',
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
                    $translatePartialLoader.addPart('recipeMaster');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('recipe-master-my-suffix-detail', {
            parent: 'recipe-master-my-suffix',
            url: '/recipe-master-my-suffix/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'coolcookApp.recipeMaster.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/recipe-master/recipe-master-my-suffix-detail.html',
                    controller: 'RecipeMasterMySuffixDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('recipeMaster');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'RecipeMaster', function($stateParams, RecipeMaster) {
                    return RecipeMaster.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'recipe-master-my-suffix',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('recipe-master-my-suffix-detail.edit', {
            parent: 'recipe-master-my-suffix-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/recipe-master/recipe-master-my-suffix-dialog.html',
                    controller: 'RecipeMasterMySuffixDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['RecipeMaster', function(RecipeMaster) {
                            return RecipeMaster.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('recipe-master-my-suffix.new', {
            parent: 'recipe-master-my-suffix',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/recipe-master/recipe-master-my-suffix-dialog.html',
                    controller: 'RecipeMasterMySuffixDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                name: null,
                                desc: null,
                                process: null,
                                durationInMin: null,
                                image: null,
                                imageContentType: null,
                                isOriginal: null,
                                originalRecipeId: null,
                                isVeg: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('recipe-master-my-suffix', null, { reload: 'recipe-master-my-suffix' });
                }, function() {
                    $state.go('recipe-master-my-suffix');
                });
            }]
        })
        .state('recipe-master-my-suffix.edit', {
            parent: 'recipe-master-my-suffix',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/recipe-master/recipe-master-my-suffix-dialog.html',
                    controller: 'RecipeMasterMySuffixDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['RecipeMaster', function(RecipeMaster) {
                            return RecipeMaster.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('recipe-master-my-suffix', null, { reload: 'recipe-master-my-suffix' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('recipe-master-my-suffix.delete', {
            parent: 'recipe-master-my-suffix',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/recipe-master/recipe-master-my-suffix-delete-dialog.html',
                    controller: 'RecipeMasterMySuffixDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['RecipeMaster', function(RecipeMaster) {
                            return RecipeMaster.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('recipe-master-my-suffix', null, { reload: 'recipe-master-my-suffix' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();

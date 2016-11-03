(function() {
    'use strict';
    angular
        .module('rhinobuyApp')
        .factory('Orders', Orders);

    Orders.$inject = ['$resource', 'DateUtils'];

    function Orders ($resource, DateUtils) {
        var resourceUrl =  'api/orders/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                        data.datePlaced = DateUtils.convertDateTimeFromServer(data.datePlaced);
                    }
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();

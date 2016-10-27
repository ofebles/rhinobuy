(function() {
    'use strict';
    angular
        .module('rhinobuyApp')
        .factory('Shipment', Shipment);

    Shipment.$inject = ['$resource', 'DateUtils'];

    function Shipment ($resource, DateUtils) {
        var resourceUrl =  'api/shipments/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                        data.shipmentDate = DateUtils.convertLocalDateFromServer(data.shipmentDate);
                    }
                    return data;
                }
            },
            'update': {
                method: 'PUT',
                transformRequest: function (data) {
                    var copy = angular.copy(data);
                    copy.shipmentDate = DateUtils.convertLocalDateToServer(copy.shipmentDate);
                    return angular.toJson(copy);
                }
            },
            'save': {
                method: 'POST',
                transformRequest: function (data) {
                    var copy = angular.copy(data);
                    copy.shipmentDate = DateUtils.convertLocalDateToServer(copy.shipmentDate);
                    return angular.toJson(copy);
                }
            }
        });
    }
})();

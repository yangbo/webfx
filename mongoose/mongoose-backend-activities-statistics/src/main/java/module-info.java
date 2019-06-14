// Generated by WebFx

module mongoose.backend.activities.statistics {

    // Direct dependencies modules
    requires java.base;
    requires javafx.base;
    requires javafx.controls;
    requires javafx.graphics;
    requires mongoose.backend.activities.bookings;
    requires mongoose.client.activity;
    requires mongoose.client.entities;
    requires mongoose.client.util;
    requires mongoose.shared.entities;
    requires webfx.framework.client.action;
    requires webfx.framework.client.activity;
    requires webfx.framework.client.controls;
    requires webfx.framework.client.domain;
    requires webfx.framework.client.uifilter;
    requires webfx.framework.client.uirouter;
    requires webfx.framework.shared.domain;
    requires webfx.framework.shared.entity;
    requires webfx.framework.shared.expression;
    requires webfx.framework.shared.operation;
    requires webfx.framework.shared.router;
    requires webfx.fxkit.extracontrols;
    requires webfx.fxkit.extratype;
    requires webfx.fxkit.util;
    requires webfx.platform.client.windowhistory;
    requires webfx.platform.shared.util;

    // Exported packages
    exports mongoose.backend.activities.statistics;
    exports mongoose.backend.activities.statistics.routing;
    exports mongoose.backend.operations.statistics;

    // Provided services
    provides webfx.framework.client.operations.route.RouteRequestEmitter with mongoose.backend.activities.statistics.RouteToStatisticsRequestEmitter;
    provides webfx.framework.client.ui.uirouter.UiRoute with mongoose.backend.activities.statistics.StatisticsUiRoute;

}
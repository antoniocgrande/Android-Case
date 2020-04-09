package br.com.antoniocgrande.truckpad_case.data.response

import com.google.gson.annotations.SerializedName
import java.io.Serializable


/* Copyright 2020.
 ************************************************************
 * Project     : TruckPad-Case
 * Description : br.com.antoniocgrande.truckpad_case.data.response
 * Created by  : antoniocgrande
 * Date        : 07/04/2020 22:29
 ************************************************************/
class RouteResponse : Serializable {
    @SerializedName("points")
    var points: List<Point>? =
        null

    @SerializedName("distance")
    var distance: Int? = null

    @SerializedName("distance_unit")
    var distanceUnit: String? = null

    @SerializedName("duration")
    var duration: Int? = null

    @SerializedName("duration_unit")
    var durationUnit: String? = null

    @SerializedName("has_tolls")
    var hasTolls: Boolean? = null

    @SerializedName("toll_count")
    var tollCount: Int? = null

    @SerializedName("toll_cost")
    var tollCost: Int? = null

    @SerializedName("toll_cost_unit")
    var tollCostUnit: String? = null

    @SerializedName("route")
    var route: List<List<List<Float>>>? =
        null

    @SerializedName("provider")
    var provider: String? = null

    @SerializedName("cached")
    var cached: Boolean? = null

    @SerializedName("fuel_usage")
    var fuelUsage: Float? = null

    @SerializedName("fuel_usage_unit")
    var fuelUsageUnit: String? = null

    @SerializedName("fuel_cost")
    var fuelCost: Float? = null

    @SerializedName("fuel_cost_unit")
    var fuelCostUnit: String? = null

    @SerializedName("total_cost")
    var totalCost: Float? = null
}
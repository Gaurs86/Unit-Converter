package converter

enum class UnitType {LENGTH, WEIGHT, TEMPERATURE,UNKNOWN}

enum class Unit(val type: UnitType, val normalizedName: String, val factor: Double) {
    METER(UnitType.LENGTH, "meter", 1.0),
    KILOMETER(UnitType.LENGTH, "kilometer", 1_000.0),
    CENTIMETER(UnitType.LENGTH, "centimeter", 0.01),
    MILLIMETER(UnitType.LENGTH, "millimeter", 0.001),
    MILE(UnitType.LENGTH, "mile", 1_609.35),
    YARD(UnitType.LENGTH, "yard", 0.9144),
    FOOT(UnitType.LENGTH, "foot", 0.3048),
    INCH(UnitType.LENGTH, "inch", 0.0254),

    GRAM(UnitType.WEIGHT, "gram", 1.0),
    KILOGRAM(UnitType.WEIGHT, "kilogram", 1_000.0),
    MILLIGRAM(UnitType.WEIGHT, "milligram", 0.001),
    POUND(UnitType.WEIGHT, "pound", 453.592),
    OUNCE(UnitType.WEIGHT, "ounce", 28.3495),

    CELSIUS(UnitType.TEMPERATURE, "degree Celsius", 0.0),
    KELVIN(UnitType.TEMPERATURE, "kelvin", 0.0),
    FAHRENHEIT(UnitType.TEMPERATURE, "degree Fahrenheit", 0.0),

    UNKNOWN(UnitType.UNKNOWN, "???", 0.0);
}
fun main() {

    while (true){
        var flag = false
        print("Enter what you want to convert (or exit): ")
        val input = readLine()!!
        if(input == "exit") break
        val inputArray = input.split(" ")
        var i=0
        var num = 0.0
        var a = ""
        var b = ""

        for(s in inputArray) {
            if(s.toLowerCase()=="degree"||s.toLowerCase()=="degrees")
                continue
            if(i==0) {
                try {
                    num = s.toDouble()
                } catch (ex: NumberFormatException) {
                    println("Parse error")
                    flag = true
                    break
                }

            }
            if(i==1)
                a = s
            if(i==3)
                b = s
            i++
        }

        if( flag) continue
        val unit1 = getNormalizedUnit(a)
        val unit2 = getNormalizedUnit(b)

        var m = 0.0
        if(unit1 == Unit.UNKNOWN || unit2 == Unit.UNKNOWN || unit1.type != unit2.type) {
            val from = plural(unit1.normalizedName)
            val to = plural(unit2.normalizedName)

            println("Conversion from $from to $to is impossible")
            continue
        }

        if (num < 0.0) {
            if (unit1.type == UnitType.LENGTH) {
                println("Length shouldn't be negative")
                continue
            } else if (unit2.type == UnitType.WEIGHT) {
                println("Weight shouldn't be negative")
                continue
            }
        }

        if(unit1.type == UnitType.TEMPERATURE && unit2.type == UnitType.TEMPERATURE) {
            when {
                unit1 == Unit.FAHRENHEIT && unit2 == Unit.FAHRENHEIT -> m = num
                unit1 == Unit.CELSIUS && unit2 == Unit.CELSIUS -> m = num
                unit1 == Unit.KELVIN && unit2 == Unit.KELVIN -> m = num
                unit1 == Unit.FAHRENHEIT && unit2 == Unit.CELSIUS -> m = (num - 32.0) * 5.0 / 9.0
                unit1 == Unit.CELSIUS && unit2 == Unit.FAHRENHEIT -> m = num * 9.0 / 5.0 + 32.0
                unit1 == Unit.CELSIUS && unit2 == Unit.KELVIN -> m = num + 273.15
                unit1 == Unit.KELVIN && unit2 == Unit.CELSIUS -> m = num - 273.15
                unit1 == Unit.KELVIN && unit2 == Unit.FAHRENHEIT -> m = num * 9.0 / 5.0 - 459.67
                unit1 == Unit.FAHRENHEIT && unit2 == Unit.KELVIN -> m = (num + 459.67) * 5.0 / 9.0
            }
        }

        else {
            m = num * unit1.factor / unit2.factor
        }

        val from = if(num == 1.0) unit1.normalizedName else plural(unit1.normalizedName)
        val to = if(m == 1.0) unit2.normalizedName else plural(unit2.normalizedName)

        println("$num $from is $m $to")

    }

}

fun plural(normalizedName: String) =  when(normalizedName.toLowerCase()) {
        "foot" -> "feet"
        "inch" -> "inches"
        "degree celsius" -> "degrees Celsius"
        "degree fahrenheit" -> "degrees Fahrenheit"
        "???" -> "???"
        else -> normalizedName + "s"
}
fun getNormalizedUnit(unit :String): Unit {
    return when (unit.toLowerCase()) {
        "m", "meter", "meters" -> Unit.METER
        "km", "kilometer", "kilometers" -> Unit.KILOMETER
        "cm", "centimeter", "centimeters" -> Unit.CENTIMETER
        "mm", "millimeter", "millimeters" -> Unit.MILLIMETER
        "mi", "mile", "miles" -> Unit.MILE
        "yd", "yard", "yards" -> Unit.YARD
        "ft", "foot", "feet" -> Unit.FOOT
        "in", "inch", "inches" -> Unit.INCH
        "g", "gram", "grams" -> Unit.GRAM
        "kg", "kilogram", "kilograms" -> Unit.KILOGRAM
        "mg", "milligram", "milligrams" -> Unit.MILLIGRAM
        "lb", "pound", "pounds" -> Unit.POUND
        "oz", "ounce", "ounces" -> Unit.OUNCE
        "c", "dc", "celsius" -> Unit.CELSIUS
        "k", "kelvin", "kelvins" -> Unit.KELVIN
        "f", "df", "fahrenheit" -> Unit.FAHRENHEIT
        else -> Unit.UNKNOWN
    }
}


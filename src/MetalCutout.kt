class MetalCutout {
    val material = materials.StainlessSteelS275JR
    val X = 90              // number of points horizontallu
    val Y = 90              // numner of points vertically
    val dx = 0.5 / X        // distance between points (m)
    val dy = 0.5 / Y        // distance between points (m)


    val shape = Mask2D(X,Y, {_,_ -> true })     // shape of the cutout
    val y0 = MaskedDoubleArray2D(shape, {_,_ -> 0.0 }) // point position at time t=0
    val y1 = MaskedDoubleArray2D(shape, {_,_ -> 0.0 }) // point position at time t=-1
    val y2 = MaskedDoubleArray2D(shape, {_,_ -> 0.0 }) // point position at time t=-2



}
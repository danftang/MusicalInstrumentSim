package numerics
// DIFFERENTIAL OPERATORS

// FIRST DERIVATIVES

fun <T : DoubleLike<T>> d_dx(F: IField<T>): IField<T> {
    return ((ShiftW(F) - ShiftE(F)) / (F.dx() * 2.0))
}

fun <T : DoubleLike<T>> fd_dx(F: IField<T>): IField<T> {
    return ((ShiftW(F) - F) / F.dx())
}

fun <T : DoubleLike<T>> bd_dx(F: IField<T>): IField<T> {
    return ((F - ShiftE(F)) / F.dx())
}

fun <T : DoubleLike<T>> d_dy(F: IField<T>): IField<T> {
    return ((ShiftS(F) - ShiftN(F)) / (F.dy() * 2.0))
}

fun <T : DoubleLike<T>> fd_dy(F: IField<T>): IField<T> {
    return ((ShiftS(F) - F) / F.dy())
}

fun <T : DoubleLike<T>> bd_dy(F: IField<T>): IField<T> {
    return ((F - ShiftN(F)) / F.dy())
}


// SECOND DERIVATIVES

fun <T : DoubleLike<T>> d2_dx2(F: IField<T>): IField<T> {
    return (ShiftW(F) - (F * 2.0) + ShiftE(F)) / (F.dx() * F.dx())
}

fun <T : DoubleLike<T>> d2_dy2(F: IField<T>): IField<T> {
    return (ShiftN(F) - (F * 2.0) + ShiftS(F)) / (F.dy() * F.dy())
}

fun <T : DoubleLike<T>> Laplacian(F: IField<T>): IField<T> {
    return (((ShiftW(F) + ShiftE(F)) * F.dy() * F.dy()) + ((ShiftS(F) + ShiftN(F)) * F.dx() * F.dx())) / (2.0 * (F.dx() * F.dx() + F.dy() * F.dy()))
}


// FOURTH DERIVATIVES

fun <T : DoubleLike<T>> d4_dx4(F: IField<T>): IField<T> {
    return d2_dx2(d2_dx2(F))
}

fun <T : DoubleLike<T>> d4_dy4(F: IField<T>): IField<T> {
    return d2_dy2(d2_dy2(F))
}


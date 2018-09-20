package materials

object StainlessSteelS275JR : PhysicalProperties {
    // Source: https://www.makeitfrom.com/material-properties/EN-1.0044-S275JR-Non-Alloy-Steel
    override val E: Double = 1.9e11     // Young's modulus
    override val G: Double = 7.3e10     // Shear modulus
    override val nu: Double = 0.29      // Poisson's ratio
    override val rho: Double = 7900.0   // density
}
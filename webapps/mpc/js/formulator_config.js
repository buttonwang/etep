function visible()
{
    //f1.SetRichUIMode(false);
    //f1.SetMathML("<math display = 'block'><mrow><mfrac><apply><plus/><ci>x</ci><ci>y</ci></apply><mroot><cn>3</cn><cn>2</cn></mroot></mfrac></mrow></math>");
    //f1.HideToolBarButton("@t@Logics@", "<=", true);
	//f1.HideToolBarButton("@t@Fence@", "()", true);
	
	//f1.EnableMathShortcuts(false);
	//f1.MultiViewBar=false;
	//f1.DefaultView(false);
	//f1.ExpressionBar(false);
	
	f1.HideToolBar("@t@Token@", true);
	f1.HideToolBar("@t@Apply@", true);
	f1.HideToolBar("@t@Piecewise@", true);
	f1.HideToolBar("@t@Arithmetic@", true);
	f1.HideToolBar("@t@Algebra@", true);
	f1.HideToolBar("@t@CMLogic@", true);
	f1.HideToolBar("@t@CMMaxMin@", true);
	f1.HideToolBar("@t@CMRelations@", true);
	f1.HideToolBar("@t@CMCalculus@", true);
	f1.HideToolBar("@t@CMSet@", true);
	f1.HideToolBar("@t@CMSum@", true);
	f1.HideToolBar("@t@Trigonometric@", true);
	f1.HideToolBar("@t@Hyperbolic@", true);
	f1.HideToolBar("@t@Exponential@", true);
	f1.HideToolBar("@t@CMStatistics@", true);
	f1.HideToolBar("@t@CMLinearAlgebra@", true);
	f1.HideToolBar("@t@CMSemantics@", true);
	f1.HideToolBar("@t@CMConstant@", true);
	f1.HideToolBar("@t@CMQualifier@", true);

	f1.HideToolBar("@t@Space@", true);
	f1.HideToolBar("@t@GreekUp@", true);
	f1.HideToolBar("@t@Sum@", true);
	f1.HideToolBar("@t@Integral@", true);
	f1.HideToolBar("@t@Products@", true);
	f1.HideToolBar("@t@Table@", true);
	f1.HideToolBar("@t@Box@", true);
	
	
	f1.HideToolBarButton("@t@Logics@", "<<", true);
	f1.HideToolBarButton("@t@Logics@", ">>", true);
	f1.HideToolBarButton("@t@Logics@", "@precedes", true);
	f1.HideToolBarButton("@t@Logics@", "@succeeds", true);
	f1.HideToolBarButton("@t@Logics@", "@normal_subgroup", true);
	f1.HideToolBarButton("@t@Logics@", "@cnormal_subgroup", true);
	f1.HideToolBarButton("@t@Logics@", "@identical", true);
	f1.HideToolBarButton("@t@Logics@", "~", true);
	f1.HideToolBarButton("@t@Logics@", "@almost_equal", true);
	f1.HideToolBarButton("@t@Logics@", "~-", true);
	f1.HideToolBarButton("@t@Logics@", "~=", true);
	f1.HideToolBarButton("@t@Logics@", "@proportional", true);
	f1.HideToolBarButton("@t@Logics@", "@therefore", true);
	f1.HideToolBarButton("@t@Logics@", "!", true);
	f1.HideToolBarButton("@t@Logics@", "&", true);
	f1.HideToolBarButton("@t@Logics@", "|", true);
	f1.HideToolBarButton("@t@Logics@", "@estimates", true);
	f1.HideToolBarButton("@t@Logics@", "@approaches", true);
	
	f1.HideToolBarButton("@t@Operator@", "@plusminus", true);
	f1.HideToolBarButton("@t@Operator@", "*", true);
	f1.HideToolBarButton("@t@Operator@", "@circle_plus", true);
	f1.HideToolBarButton("@t@Operator@", "@plus", true);
	f1.HideToolBarButton("@t@Operator@", "@minus", true);
	f1.HideToolBarButton("@t@Operator@", "@bullet", true);
	f1.HideToolBarButton("@t@Operator@", "@lang", true);
	f1.HideToolBarButton("@t@Operator@", "@rang", true);

	f1.HideToolBarButton("@t@Arrow@", "@arrow_h", true);
	f1.HideToolBarButton("@t@Arrow@", "@arrow_l", true);
	f1.HideToolBarButton("@t@Arrow@", "@arrow_v", true);
	f1.HideToolBarButton("@t@Arrow@", "@darrow_h", true);
	f1.HideToolBarButton("@t@Arrow@", "@darrow_r", true);
	f1.HideToolBarButton("@t@Arrow@", "@darrow_l", true);
	f1.HideToolBarButton("@t@Arrow@", "@darrow_v", true);
	f1.HideToolBarButton("@t@Arrow@", "@rang", true);
	f1.HideToolBarButton("@t@Arrow@", "@darrow_u", true);
	f1.HideToolBarButton("@t@Arrow@", "@darrow_d", true);
	f1.HideToolBarButton("@t@Arrow@", "@aarrow_ne", true);
	f1.HideToolBarButton("@t@Arrow@", "@aarrow_sw", true);
	f1.HideToolBarButton("@t@Arrow@", "@aarrow_se", true);
	f1.HideToolBarButton("@t@Arrow@", "@aarrow_nw", true);
	f1.HideToolBarButton("@t@Arrow@", "@aarrow_rlarr", true);	
	f1.HideToolBarButton("@t@Arrow@", "@arrow_map", true);
	f1.HideToolBarButton("@t@Arrow@", "@arrow_corner", true);

	f1.HideToolBarButton("@t@Const@", "@capitaldifferentiald", true);
	f1.HideToolBarButton("@t@Const@", "@differentiald", true);
	f1.HideToolBarButton("@t@Const@", "@partiald", true);
	f1.HideToolBarButton("@t@Const@", "@exponentiale", true);
	f1.HideToolBarButton("@t@Const@", "@imaginaryi", true);
	f1.HideToolBarButton("@t@Const@", "@planck2pi", true);
	f1.HideToolBarButton("@t@Const@", "@lambda_stroke", true);
	f1.HideToolBarButton("@t@Const@", "@ell", true);
	
	
	f1.HideToolBarButton("@t@Misc@", "@weierstrass", true);
	f1.HideToolBarButton("@t@Misc@", "imag", true);
	f1.HideToolBarButton("@t@Misc@", "real", true);
	f1.HideToolBarButton("@t@Misc@", "@aleph", true);
	f1.HideToolBarButton("@t@Misc@", "@reals", true);
	f1.HideToolBarButton("@t@Misc@", "@integers", true);
	f1.HideToolBarButton("@t@Misc@", "@complexes", true);
	f1.HideToolBarButton("@t@Misc@", "@rationals", true);
	f1.HideToolBarButton("@t@Misc@", "@naturals", true);
	f1.HideToolBarButton("@t@Misc@", "@dagger", true);
	f1.HideToolBarButton("@t@Misc@", "@gradient", true);
	f1.HideToolBarButton("@t@Misc@", "@mho", true);
	f1.HideToolBarButton("@t@Misc@", "@diam", true);
	f1.HideToolBarButton("@t@Misc@", "@sum", true);
	f1.HideToolBarButton("@t@Misc@", "@product", true);
	f1.HideToolBarButton("@t@Misc@", "@coproduct", true);
	f1.HideToolBarButton("@t@Misc@", "@integral", true);
	f1.HideToolBarButton("@t@Misc@", "@angmsd", true);
	f1.HideToolBarButton("@t@Misc@", "@angsph", true);
	f1.HideToolBarButton("@t@Misc@", "@parallel", true);

	f1.HideToolBarButton("@t@GreekLo@", "iota", true);
	f1.HideToolBarButton("@t@GreekLo@", "kappa", true);
	f1.HideToolBarButton("@t@GreekLo@", "ogr", true);
	f1.HideToolBarButton("@t@GreekLo@", "pi", true);
	f1.HideToolBarButton("@t@GreekLo@", "sigmav", true);
	f1.HideToolBarButton("@t@GreekLo@", "upsilon", true);
	f1.HideToolBarButton("@t@GreekLo@", "phi", true);
	f1.HideToolBarButton("@t@GreekLo@", "chi", true);
	f1.HideToolBarButton("@t@GreekLo@", "psi", true);
	

	f1.HideToolBarButton("@t@Fence@", "()", true);
	f1.HideToolBarButton("@t@Fence@", "[]", true);
	f1.HideToolBarButton("@t@Fence@", "{}", true);
	f1.HideToolBarButton("@t@Fence@", "<>", true);
	f1.HideToolBarButton("@t@Fence@", "@abs", true);
	f1.HideToolBarButton("@t@Fence@", "@norm", true);
	f1.HideToolBarButton("@t@Fence@", "@floor", true);
	f1.HideToolBarButton("@t@Fence@", "@ceil", true);
	f1.HideToolBarButton("@t@Fence@", "(", true);
	f1.HideToolBarButton("@t@Fence@", ")", true);
	f1.HideToolBarButton("@t@Fence@", "[", true);
	f1.HideToolBarButton("@t@Fence@", "]", true);
	f1.HideToolBarButton("@t@Fence@", "}", true);
	f1.HideToolBarButton("@t@Fence@", "<", true);
	f1.HideToolBarButton("@t@Fence@", ">", true);
	f1.HideToolBarButton("@t@Fence@", "| left", true);
	f1.HideToolBarButton("@t@Fence@", "| right", true);
	f1.HideToolBarButton("@t@Fence@", "|| left", true);
	f1.HideToolBarButton("@t@Fence@", "|| right", true);

	f1.HideToolBarButton("@t@Fraction@", "@diagonal_fraction", true);
	f1.HideToolBarButton("@t@Fraction@", "/", true);
	f1.HideToolBarButton("@t@Fraction@", "@longdiv", true);

	f1.HideToolBarButton("@t@Script@", "@suppresup", true);
	f1.HideToolBarButton("@t@Script@", "@subpresub", true);
	f1.HideToolBarButton("@t@Script@", "@suppresup_subpresub", true);
	f1.HideToolBarButton("@t@Script@", "@sup_presub", true);
	f1.HideToolBarButton("@t@Script@", "@sub_presup", true);
	f1.HideToolBarButton("@t@Script@", "@supsub_presub", true);
	f1.HideToolBarButton("@t@Script@", "@sub_presupsub", true);
	f1.HideToolBarButton("@t@Script@", "@sup_presupsub", true);
	f1.HideToolBarButton("@t@Script@", "@supsub_presup", true);


	f1.HideToolBarButton("@t@Bars@", "@tilde", true);
	f1.HideToolBarButton("@t@Bars@", "@hat", true);	
	f1.HideToolBarButton("@t@Bars@", "@jointstatus", true);
	f1.HideToolBarButton("@t@Bars@", "@doverbar", true);
	f1.HideToolBarButton("@t@Bars@", "@underbar", true);
	f1.HideToolBarButton("@t@Bars@", "@laoverbar", true);
	f1.HideToolBarButton("@t@Bars@", "@rhoverbar", true);
	f1.HideToolBarButton("@t@Bars@", "@daoverbar", true);
	f1.HideToolBarButton("@t@Bars@", "@raunderbar", true);
	f1.HideToolBarButton("@t@Bars@", "@launderbar", true);
	f1.HideToolBarButton("@t@Bars@", "@rhunderbar", true);
	f1.HideToolBarButton("@t@Bars@", "@daunderbar", true);
	f1.HideToolBarButton("@t@Bars@", "@overbrace", true);
	f1.HideToolBarButton("@t@Bars@", "@underbrace", true);
	f1.HideToolBarButton("@t@Bars@", "@hormidstrike", true);
	f1.HideToolBarButton("@t@Bars@", "@vermidstrike", true);
	f1.HideToolBarButton("@t@Bars@", "@strike", true);
	f1.HideToolBarButton("@t@Bars@", "@strike_blur", true);
	f1.HideToolBarButton("@t@Bars@", "@strike_tlbr", true);

	f1.HideToolBarButton("@t@Labels@", "@ralslot", true);
	f1.HideToolBarButton("@t@Labels@", "@lauslot", true);
	f1.HideToolBarButton("@t@Labels@", "@lalslot", true);
	f1.HideToolBarButton("@t@Labels@", "@laulslot", true);
	f1.HideToolBarButton("@t@Labels@", "@dauslot", true);
	f1.HideToolBarButton("@t@Labels@", "@dalslot", true);
	f1.HideToolBarButton("@t@Labels@", "@daulslot", true);
	f1.HideToolBarButton("@t@Labels@", "@ddauslot", true);
	f1.HideToolBarButton("@t@Labels@", "@ddalslot", true);
	f1.HideToolBarButton("@t@Labels@", "@ddaulslot", true);
	f1.HideToolBarButton("@t@Labels@", "@ddlsauslot", true);
	f1.HideToolBarButton("@t@Labels@", "@ddlsalslot", true);
	f1.HideToolBarButton("@t@Labels@", "@ddlsaulslot", true);
	f1.HideToolBarButton("@t@Labels@", "@ddslauslot", true);
	f1.HideToolBarButton("@t@Labels@", "@ddslalslot", true);
	f1.HideToolBarButton("@t@Labels@", "@ddslaulslot", true);
	f1.HideToolBarButton("@t@Labels@", "@hddalslot", true);
	f1.HideToolBarButton("@t@Labels@", "@hddlsauslot", true);
	f1.HideToolBarButton("@t@Labels@", "@hddlsalslot", true);
	f1.HideToolBarButton("@t@Labels@", "@hddlsaulslot", true);
	f1.HideToolBarButton("@t@Labels@", "@hddslauslot", true);
	f1.HideToolBarButton("@t@Labels@", "@hddslalslot", true);
	f1.HideToolBarButton("@t@Labels@", "@hddslaulslot", true);

}
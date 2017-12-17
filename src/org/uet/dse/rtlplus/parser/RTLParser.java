// $ANTLR 3.5.1 /home/pnh/NCKH/workspace-java/RTLPlus/src/org/uet/dse/rtlplus/parser/RTL.g 2017-12-17 17:43:39

/*
 *  @author Duc-Hanh Dang, Khoa-Hai Nguyen, Xuan-Loi Vu
 */

package org.uet.dse.rtlplus.parser;

import org.tzi.use.parser.base.BaseParser;
import org.uet.dse.rtlplus.parser.ast.*;


import org.antlr.runtime.*;
import java.util.Stack;
import java.util.List;
import java.util.ArrayList;

@SuppressWarnings("all")
public class RTLParser extends BaseParser {
	public static final String[] tokenNames = new String[] {
		"<invalid>", "<EOR>", "<DOWN>", "<UP>", "ARROW", "AT", "BAR", "COLON", 
		"COLON_COLON", "COLON_EQUAL", "COMMA", "COND_EXPR", "DOT", "DOTDOT", "EQUAL", 
		"ESC", "GREATER", "GREATER_EQUAL", "HASH", "HEX_DIGIT", "IDENT", "INT", 
		"LBRACE", "LBRACK", "LESS", "LESS_EQUAL", "LPAREN", "MINUS", "ML_COMMENT", 
		"NEWLINE", "NON_OCL_STRING", "NOT_EQUAL", "PLUS", "RANGE_OR_INT", "RBRACE", 
		"RBRACK", "REAL", "RPAREN", "SEMI", "SLASH", "SL_COMMENT", "STAR", "STRING", 
		"VOCAB", "WS", "'as'", "'backward'", "'checkCorr'", "'checkSource'", "'checkTarget'", 
		"'co-evolution'", "'direction'", "'end'", "'forward'", "'in'", "'integration'", 
		"'rule'", "'synchronization'", "'transformation'"
	};
	public static final int EOF=-1;
	public static final int T__45=45;
	public static final int T__46=46;
	public static final int T__47=47;
	public static final int T__48=48;
	public static final int T__49=49;
	public static final int T__50=50;
	public static final int T__51=51;
	public static final int T__52=52;
	public static final int T__53=53;
	public static final int T__54=54;
	public static final int T__55=55;
	public static final int T__56=56;
	public static final int T__57=57;
	public static final int T__58=58;
	public static final int ARROW=4;
	public static final int AT=5;
	public static final int BAR=6;
	public static final int COLON=7;
	public static final int COLON_COLON=8;
	public static final int COLON_EQUAL=9;
	public static final int COMMA=10;
	public static final int COND_EXPR=11;
	public static final int DOT=12;
	public static final int DOTDOT=13;
	public static final int EQUAL=14;
	public static final int ESC=15;
	public static final int GREATER=16;
	public static final int GREATER_EQUAL=17;
	public static final int HASH=18;
	public static final int HEX_DIGIT=19;
	public static final int IDENT=20;
	public static final int INT=21;
	public static final int LBRACE=22;
	public static final int LBRACK=23;
	public static final int LESS=24;
	public static final int LESS_EQUAL=25;
	public static final int LPAREN=26;
	public static final int MINUS=27;
	public static final int ML_COMMENT=28;
	public static final int NEWLINE=29;
	public static final int NON_OCL_STRING=30;
	public static final int NOT_EQUAL=31;
	public static final int PLUS=32;
	public static final int RANGE_OR_INT=33;
	public static final int RBRACE=34;
	public static final int RBRACK=35;
	public static final int REAL=36;
	public static final int RPAREN=37;
	public static final int SEMI=38;
	public static final int SLASH=39;
	public static final int SL_COMMENT=40;
	public static final int STAR=41;
	public static final int STRING=42;
	public static final int VOCAB=43;
	public static final int WS=44;

	// delegates
	public BaseParser[] getDelegates() {
		return new BaseParser[] {};
	}

	// delegators


	public RTLParser(TokenStream input) {
		this(input, new RecognizerSharedState());
	}
	public RTLParser(TokenStream input, RecognizerSharedState state) {
		super(input, state);
	}

	@Override public String[] getTokenNames() { return RTLParser.tokenNames; }
	@Override public String getGrammarFileName() { return "/home/pnh/NCKH/workspace-java/RTLPlus/src/org/uet/dse/rtlplus/parser/RTL.g"; }



	// $ANTLR start "tggRuleCollection"
	// /home/pnh/NCKH/workspace-java/RTLPlus/src/org/uet/dse/rtlplus/parser/RTL.g:40:1: tggRuleCollection returns [AstRuleCollection n] : 'transformation' name= IDENT 'direction' ( 'forward' | 'backward' | 'integration' | 'co-evolution' | 'synchronization' ) (rule= tggRuleDefinition )+ EOF ;
	public final AstRuleCollection tggRuleCollection() throws RecognitionException {
		AstRuleCollection n = null;


		Token name=null;
		AstTggRule rule =null;

		try {
			// /home/pnh/NCKH/workspace-java/RTLPlus/src/org/uet/dse/rtlplus/parser/RTL.g:41:2: ( 'transformation' name= IDENT 'direction' ( 'forward' | 'backward' | 'integration' | 'co-evolution' | 'synchronization' ) (rule= tggRuleDefinition )+ EOF )
			// /home/pnh/NCKH/workspace-java/RTLPlus/src/org/uet/dse/rtlplus/parser/RTL.g:42:3: 'transformation' name= IDENT 'direction' ( 'forward' | 'backward' | 'integration' | 'co-evolution' | 'synchronization' ) (rule= tggRuleDefinition )+ EOF
			{
			match(input,58,FOLLOW_58_in_tggRuleCollection56); 
			name=(Token)match(input,IDENT,FOLLOW_IDENT_in_tggRuleCollection60); 
			 n = new AstRuleCollection((name!=null?name.getText():null)); 
			match(input,51,FOLLOW_51_in_tggRuleCollection68); 
			if ( input.LA(1)==46||input.LA(1)==50||input.LA(1)==53||input.LA(1)==55||input.LA(1)==57 ) {
				input.consume();
				state.errorRecovery=false;
			}
			else {
				MismatchedSetException mse = new MismatchedSetException(null,input);
				throw mse;
			}
			// /home/pnh/NCKH/workspace-java/RTLPlus/src/org/uet/dse/rtlplus/parser/RTL.g:44:3: (rule= tggRuleDefinition )+
			int cnt1=0;
			loop1:
			while (true) {
				int alt1=2;
				int LA1_0 = input.LA(1);
				if ( (LA1_0==56) ) {
					alt1=1;
				}

				switch (alt1) {
				case 1 :
					// /home/pnh/NCKH/workspace-java/RTLPlus/src/org/uet/dse/rtlplus/parser/RTL.g:44:4: rule= tggRuleDefinition
					{
					pushFollow(FOLLOW_tggRuleDefinition_in_tggRuleCollection87);
					rule=tggRuleDefinition();
					state._fsp--;

					 n.addRuleDefinition(rule); 
					}
					break;

				default :
					if ( cnt1 >= 1 ) break loop1;
					EarlyExitException eee = new EarlyExitException(1, input);
					throw eee;
				}
				cnt1++;
			}

			match(input,EOF,FOLLOW_EOF_in_tggRuleCollection96); 
			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving
		}
		return n;
	}
	// $ANTLR end "tggRuleCollection"



	// $ANTLR start "tggRuleDefinition"
	// /home/pnh/NCKH/workspace-java/RTLPlus/src/org/uet/dse/rtlplus/parser/RTL.g:48:1: tggRuleDefinition returns [AstTggRule n] : 'rule' name= IDENT 'checkSource' src= ruleDefinition 'checkTarget' trg= ruleDefinition 'checkCorr' corr= corrRuleDefinition 'end' ;
	public final AstTggRule tggRuleDefinition() throws RecognitionException {
		AstTggRule n = null;


		Token name=null;
		AstRule src =null;
		AstRule trg =null;
		AstCorrRule corr =null;

		try {
			// /home/pnh/NCKH/workspace-java/RTLPlus/src/org/uet/dse/rtlplus/parser/RTL.g:49:2: ( 'rule' name= IDENT 'checkSource' src= ruleDefinition 'checkTarget' trg= ruleDefinition 'checkCorr' corr= corrRuleDefinition 'end' )
			// /home/pnh/NCKH/workspace-java/RTLPlus/src/org/uet/dse/rtlplus/parser/RTL.g:50:3: 'rule' name= IDENT 'checkSource' src= ruleDefinition 'checkTarget' trg= ruleDefinition 'checkCorr' corr= corrRuleDefinition 'end'
			{
			match(input,56,FOLLOW_56_in_tggRuleDefinition114); 
			name=(Token)match(input,IDENT,FOLLOW_IDENT_in_tggRuleDefinition118); 
			match(input,48,FOLLOW_48_in_tggRuleDefinition122); 
			pushFollow(FOLLOW_ruleDefinition_in_tggRuleDefinition126);
			src=ruleDefinition();
			state._fsp--;

			match(input,49,FOLLOW_49_in_tggRuleDefinition130); 
			pushFollow(FOLLOW_ruleDefinition_in_tggRuleDefinition134);
			trg=ruleDefinition();
			state._fsp--;

			match(input,47,FOLLOW_47_in_tggRuleDefinition138); 
			pushFollow(FOLLOW_corrRuleDefinition_in_tggRuleDefinition142);
			corr=corrRuleDefinition();
			state._fsp--;

			match(input,52,FOLLOW_52_in_tggRuleDefinition146); 
			 n = new AstTggRule((name!=null?name.getText():null), src, trg, corr); 
			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving
		}
		return n;
	}
	// $ANTLR end "tggRuleDefinition"



	// $ANTLR start "ruleDefinition"
	// /home/pnh/NCKH/workspace-java/RTLPlus/src/org/uet/dse/rtlplus/parser/RTL.g:59:1: ruleDefinition returns [AstRule n] : LPAREN lhs= patternDefinition RPAREN LBRACE rhs= patternDefinition RBRACE ;
	public final AstRule ruleDefinition() throws RecognitionException {
		AstRule n = null;


		AstPattern lhs =null;
		AstPattern rhs =null;

		try {
			// /home/pnh/NCKH/workspace-java/RTLPlus/src/org/uet/dse/rtlplus/parser/RTL.g:60:2: ( LPAREN lhs= patternDefinition RPAREN LBRACE rhs= patternDefinition RBRACE )
			// /home/pnh/NCKH/workspace-java/RTLPlus/src/org/uet/dse/rtlplus/parser/RTL.g:61:3: LPAREN lhs= patternDefinition RPAREN LBRACE rhs= patternDefinition RBRACE
			{
			match(input,LPAREN,FOLLOW_LPAREN_in_ruleDefinition169); 
			pushFollow(FOLLOW_patternDefinition_in_ruleDefinition175);
			lhs=patternDefinition();
			state._fsp--;

			match(input,RPAREN,FOLLOW_RPAREN_in_ruleDefinition179); 
			match(input,LBRACE,FOLLOW_LBRACE_in_ruleDefinition181); 
			pushFollow(FOLLOW_patternDefinition_in_ruleDefinition187);
			rhs=patternDefinition();
			state._fsp--;

			match(input,RBRACE,FOLLOW_RBRACE_in_ruleDefinition191); 
			 n = new AstRule(lhs, rhs); 
			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving
		}
		return n;
	}
	// $ANTLR end "ruleDefinition"



	// $ANTLR start "corrRuleDefinition"
	// /home/pnh/NCKH/workspace-java/RTLPlus/src/org/uet/dse/rtlplus/parser/RTL.g:69:1: corrRuleDefinition returns [AstCorrRule n] : LPAREN lhs= corrPatternDefinition RPAREN LBRACE rhs= corrPatternDefinition RBRACE ;
	public final AstCorrRule corrRuleDefinition() throws RecognitionException {
		AstCorrRule n = null;


		AstCorr lhs =null;
		AstCorr rhs =null;

		try {
			// /home/pnh/NCKH/workspace-java/RTLPlus/src/org/uet/dse/rtlplus/parser/RTL.g:70:2: ( LPAREN lhs= corrPatternDefinition RPAREN LBRACE rhs= corrPatternDefinition RBRACE )
			// /home/pnh/NCKH/workspace-java/RTLPlus/src/org/uet/dse/rtlplus/parser/RTL.g:71:3: LPAREN lhs= corrPatternDefinition RPAREN LBRACE rhs= corrPatternDefinition RBRACE
			{
			match(input,LPAREN,FOLLOW_LPAREN_in_corrRuleDefinition214); 
			pushFollow(FOLLOW_corrPatternDefinition_in_corrRuleDefinition220);
			lhs=corrPatternDefinition();
			state._fsp--;

			match(input,RPAREN,FOLLOW_RPAREN_in_corrRuleDefinition224); 
			match(input,LBRACE,FOLLOW_LBRACE_in_corrRuleDefinition226); 
			pushFollow(FOLLOW_corrPatternDefinition_in_corrRuleDefinition232);
			rhs=corrPatternDefinition();
			state._fsp--;

			match(input,RBRACE,FOLLOW_RBRACE_in_corrRuleDefinition236); 
			 n = new AstCorrRule(lhs, rhs); 
			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving
		}
		return n;
	}
	// $ANTLR end "corrRuleDefinition"



	// $ANTLR start "patternDefinition"
	// /home/pnh/NCKH/workspace-java/RTLPlus/src/org/uet/dse/rtlplus/parser/RTL.g:79:1: patternDefinition returns [AstPattern n] : (obj= objectDefinition )* (lnk= linkDefinition )* (cond= conditionDefinition )* ;
	public final AstPattern patternDefinition() throws RecognitionException {
		AstPattern n = null;


		AstObject obj =null;
		AstLink lnk =null;
		AstCondition cond =null;


			n = new AstPattern();

		try {
			// /home/pnh/NCKH/workspace-java/RTLPlus/src/org/uet/dse/rtlplus/parser/RTL.g:83:2: ( (obj= objectDefinition )* (lnk= linkDefinition )* (cond= conditionDefinition )* )
			// /home/pnh/NCKH/workspace-java/RTLPlus/src/org/uet/dse/rtlplus/parser/RTL.g:84:3: (obj= objectDefinition )* (lnk= linkDefinition )* (cond= conditionDefinition )*
			{
			// /home/pnh/NCKH/workspace-java/RTLPlus/src/org/uet/dse/rtlplus/parser/RTL.g:84:3: (obj= objectDefinition )*
			loop2:
			while (true) {
				int alt2=2;
				int LA2_0 = input.LA(1);
				if ( (LA2_0==IDENT) ) {
					alt2=1;
				}

				switch (alt2) {
				case 1 :
					// /home/pnh/NCKH/workspace-java/RTLPlus/src/org/uet/dse/rtlplus/parser/RTL.g:84:4: obj= objectDefinition
					{
					pushFollow(FOLLOW_objectDefinition_in_patternDefinition267);
					obj=objectDefinition();
					state._fsp--;

					 n.addObject(obj); 
					}
					break;

				default :
					break loop2;
				}
			}

			// /home/pnh/NCKH/workspace-java/RTLPlus/src/org/uet/dse/rtlplus/parser/RTL.g:85:3: (lnk= linkDefinition )*
			loop3:
			while (true) {
				int alt3=2;
				int LA3_0 = input.LA(1);
				if ( (LA3_0==LPAREN) ) {
					alt3=1;
				}

				switch (alt3) {
				case 1 :
					// /home/pnh/NCKH/workspace-java/RTLPlus/src/org/uet/dse/rtlplus/parser/RTL.g:85:4: lnk= linkDefinition
					{
					pushFollow(FOLLOW_linkDefinition_in_patternDefinition278);
					lnk=linkDefinition();
					state._fsp--;

					 n.addLink(lnk); 
					}
					break;

				default :
					break loop3;
				}
			}

			// /home/pnh/NCKH/workspace-java/RTLPlus/src/org/uet/dse/rtlplus/parser/RTL.g:86:3: (cond= conditionDefinition )*
			loop4:
			while (true) {
				int alt4=2;
				int LA4_0 = input.LA(1);
				if ( (LA4_0==COND_EXPR) ) {
					alt4=1;
				}

				switch (alt4) {
				case 1 :
					// /home/pnh/NCKH/workspace-java/RTLPlus/src/org/uet/dse/rtlplus/parser/RTL.g:86:4: cond= conditionDefinition
					{
					pushFollow(FOLLOW_conditionDefinition_in_patternDefinition289);
					cond=conditionDefinition();
					state._fsp--;

					 n.addCondition(cond); 
					}
					break;

				default :
					break loop4;
				}
			}

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving
		}
		return n;
	}
	// $ANTLR end "patternDefinition"



	// $ANTLR start "corrPatternDefinition"
	// /home/pnh/NCKH/workspace-java/RTLPlus/src/org/uet/dse/rtlplus/parser/RTL.g:89:1: corrPatternDefinition returns [AstCorr n] : (corrLnk= corrLinkDefinition )* (inv= invariantTGG )* ;
	public final AstCorr corrPatternDefinition() throws RecognitionException {
		AstCorr n = null;


		AstCorrLink corrLnk =null;
		AstInvariantTgg inv =null;


			n = new AstCorr();

		try {
			// /home/pnh/NCKH/workspace-java/RTLPlus/src/org/uet/dse/rtlplus/parser/RTL.g:93:2: ( (corrLnk= corrLinkDefinition )* (inv= invariantTGG )* )
			// /home/pnh/NCKH/workspace-java/RTLPlus/src/org/uet/dse/rtlplus/parser/RTL.g:95:3: (corrLnk= corrLinkDefinition )* (inv= invariantTGG )*
			{
			// /home/pnh/NCKH/workspace-java/RTLPlus/src/org/uet/dse/rtlplus/parser/RTL.g:95:3: (corrLnk= corrLinkDefinition )*
			loop5:
			while (true) {
				int alt5=2;
				int LA5_0 = input.LA(1);
				if ( (LA5_0==LPAREN) ) {
					alt5=1;
				}

				switch (alt5) {
				case 1 :
					// /home/pnh/NCKH/workspace-java/RTLPlus/src/org/uet/dse/rtlplus/parser/RTL.g:95:4: corrLnk= corrLinkDefinition
					{
					pushFollow(FOLLOW_corrLinkDefinition_in_corrPatternDefinition322);
					corrLnk=corrLinkDefinition();
					state._fsp--;

					 n.addCorrLink(corrLnk); 
					}
					break;

				default :
					break loop5;
				}
			}

			// /home/pnh/NCKH/workspace-java/RTLPlus/src/org/uet/dse/rtlplus/parser/RTL.g:97:3: (inv= invariantTGG )*
			loop6:
			while (true) {
				int alt6=2;
				int LA6_0 = input.LA(1);
				if ( (LA6_0==IDENT) ) {
					alt6=1;
				}

				switch (alt6) {
				case 1 :
					// /home/pnh/NCKH/workspace-java/RTLPlus/src/org/uet/dse/rtlplus/parser/RTL.g:97:4: inv= invariantTGG
					{
					pushFollow(FOLLOW_invariantTGG_in_corrPatternDefinition336);
					inv=invariantTGG();
					state._fsp--;

					 n.addInv(inv); 
					}
					break;

				default :
					break loop6;
				}
			}

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving
		}
		return n;
	}
	// $ANTLR end "corrPatternDefinition"



	// $ANTLR start "invariantTGG"
	// /home/pnh/NCKH/workspace-java/RTLPlus/src/org/uet/dse/rtlplus/parser/RTL.g:102:1: invariantTGG returns [AstInvariantTgg n] : name= IDENT COLON (cond= conditionDefinition )+ ;
	public final AstInvariantTgg invariantTGG() throws RecognitionException {
		AstInvariantTgg n = null;


		Token name=null;
		AstCondition cond =null;

		try {
			// /home/pnh/NCKH/workspace-java/RTLPlus/src/org/uet/dse/rtlplus/parser/RTL.g:103:2: (name= IDENT COLON (cond= conditionDefinition )+ )
			// /home/pnh/NCKH/workspace-java/RTLPlus/src/org/uet/dse/rtlplus/parser/RTL.g:104:3: name= IDENT COLON (cond= conditionDefinition )+
			{
			name=(Token)match(input,IDENT,FOLLOW_IDENT_in_invariantTGG366); 
			 n = new AstInvariantTgg((name!=null?name.getText():null)); 
			match(input,COLON,FOLLOW_COLON_in_invariantTGG372); 
			// /home/pnh/NCKH/workspace-java/RTLPlus/src/org/uet/dse/rtlplus/parser/RTL.g:106:3: (cond= conditionDefinition )+
			int cnt7=0;
			loop7:
			while (true) {
				int alt7=2;
				int LA7_0 = input.LA(1);
				if ( (LA7_0==COND_EXPR) ) {
					alt7=1;
				}

				switch (alt7) {
				case 1 :
					// /home/pnh/NCKH/workspace-java/RTLPlus/src/org/uet/dse/rtlplus/parser/RTL.g:106:4: cond= conditionDefinition
					{
					pushFollow(FOLLOW_conditionDefinition_in_invariantTGG380);
					cond=conditionDefinition();
					state._fsp--;

					 n.addCondition(cond); 
					}
					break;

				default :
					if ( cnt7 >= 1 ) break loop7;
					EarlyExitException eee = new EarlyExitException(7, input);
					throw eee;
				}
				cnt7++;
			}

			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving
		}
		return n;
	}
	// $ANTLR end "invariantTGG"



	// $ANTLR start "objectDefinition"
	// /home/pnh/NCKH/workspace-java/RTLPlus/src/org/uet/dse/rtlplus/parser/RTL.g:117:1: objectDefinition returns [AstObject n] : obj= IDENT COLON cls= IDENT ;
	public final AstObject objectDefinition() throws RecognitionException {
		AstObject n = null;


		Token obj=null;
		Token cls=null;

		try {
			// /home/pnh/NCKH/workspace-java/RTLPlus/src/org/uet/dse/rtlplus/parser/RTL.g:118:2: (obj= IDENT COLON cls= IDENT )
			// /home/pnh/NCKH/workspace-java/RTLPlus/src/org/uet/dse/rtlplus/parser/RTL.g:119:3: obj= IDENT COLON cls= IDENT
			{
			obj=(Token)match(input,IDENT,FOLLOW_IDENT_in_objectDefinition409); 
			match(input,COLON,FOLLOW_COLON_in_objectDefinition411); 
			cls=(Token)match(input,IDENT,FOLLOW_IDENT_in_objectDefinition415); 
			 n = new AstObject((obj!=null?obj.getText():null), (cls!=null?cls.getText():null)); 
			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving
		}
		return n;
	}
	// $ANTLR end "objectDefinition"



	// $ANTLR start "linkDefinition"
	// /home/pnh/NCKH/workspace-java/RTLPlus/src/org/uet/dse/rtlplus/parser/RTL.g:123:1: linkDefinition returns [AstLink n] : LPAREN obj1= IDENT ( COMMA obj2= IDENT )+ RPAREN COLON asc= IDENT ;
	public final AstLink linkDefinition() throws RecognitionException {
		AstLink n = null;


		Token obj1=null;
		Token obj2=null;
		Token asc=null;


			n = new AstLink();

		try {
			// /home/pnh/NCKH/workspace-java/RTLPlus/src/org/uet/dse/rtlplus/parser/RTL.g:127:2: ( LPAREN obj1= IDENT ( COMMA obj2= IDENT )+ RPAREN COLON asc= IDENT )
			// /home/pnh/NCKH/workspace-java/RTLPlus/src/org/uet/dse/rtlplus/parser/RTL.g:128:3: LPAREN obj1= IDENT ( COMMA obj2= IDENT )+ RPAREN COLON asc= IDENT
			{
			match(input,LPAREN,FOLLOW_LPAREN_in_linkDefinition442); 
			obj1=(Token)match(input,IDENT,FOLLOW_IDENT_in_linkDefinition446); 
			 n.addObject((obj1!=null?obj1.getText():null)); 
			// /home/pnh/NCKH/workspace-java/RTLPlus/src/org/uet/dse/rtlplus/parser/RTL.g:129:3: ( COMMA obj2= IDENT )+
			int cnt8=0;
			loop8:
			while (true) {
				int alt8=2;
				int LA8_0 = input.LA(1);
				if ( (LA8_0==COMMA) ) {
					alt8=1;
				}

				switch (alt8) {
				case 1 :
					// /home/pnh/NCKH/workspace-java/RTLPlus/src/org/uet/dse/rtlplus/parser/RTL.g:129:4: COMMA obj2= IDENT
					{
					match(input,COMMA,FOLLOW_COMMA_in_linkDefinition454); 
					obj2=(Token)match(input,IDENT,FOLLOW_IDENT_in_linkDefinition458); 
					 n.addObject((obj2!=null?obj2.getText():null));
					}
					break;

				default :
					if ( cnt8 >= 1 ) break loop8;
					EarlyExitException eee = new EarlyExitException(8, input);
					throw eee;
				}
				cnt8++;
			}

			match(input,RPAREN,FOLLOW_RPAREN_in_linkDefinition465); 
			match(input,COLON,FOLLOW_COLON_in_linkDefinition467); 
			asc=(Token)match(input,IDENT,FOLLOW_IDENT_in_linkDefinition473); 
			 n.setAssociation((asc!=null?asc.getText():null)); 
			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving
		}
		return n;
	}
	// $ANTLR end "linkDefinition"



	// $ANTLR start "conditionDefinition"
	// /home/pnh/NCKH/workspace-java/RTLPlus/src/org/uet/dse/rtlplus/parser/RTL.g:133:1: conditionDefinition returns [AstCondition n] : cond= COND_EXPR ;
	public final AstCondition conditionDefinition() throws RecognitionException {
		AstCondition n = null;


		Token cond=null;

		try {
			// /home/pnh/NCKH/workspace-java/RTLPlus/src/org/uet/dse/rtlplus/parser/RTL.g:134:2: (cond= COND_EXPR )
			// /home/pnh/NCKH/workspace-java/RTLPlus/src/org/uet/dse/rtlplus/parser/RTL.g:135:3: cond= COND_EXPR
			{
			cond=(Token)match(input,COND_EXPR,FOLLOW_COND_EXPR_in_conditionDefinition496); 
			 n = new AstCondition((cond!=null?cond.getText():null)); 
			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving
		}
		return n;
	}
	// $ANTLR end "conditionDefinition"



	// $ANTLR start "corrLinkDefinition"
	// /home/pnh/NCKH/workspace-java/RTLPlus/src/org/uet/dse/rtlplus/parser/RTL.g:139:1: corrLinkDefinition returns [AstCorrLink n] : LPAREN ( LPAREN srcCls= IDENT RPAREN )? srcObj= IDENT COMMA ( LPAREN trgCls= IDENT RPAREN )? trgObj= IDENT RPAREN ( 'as' LPAREN role1= IDENT COMMA role2= IDENT RPAREN )? 'in' corrObj= IDENT COLON corrCls= IDENT ;
	public final AstCorrLink corrLinkDefinition() throws RecognitionException {
		AstCorrLink n = null;


		Token srcCls=null;
		Token srcObj=null;
		Token trgCls=null;
		Token trgObj=null;
		Token role1=null;
		Token role2=null;
		Token corrObj=null;
		Token corrCls=null;


			n = new AstCorrLink();

		try {
			// /home/pnh/NCKH/workspace-java/RTLPlus/src/org/uet/dse/rtlplus/parser/RTL.g:143:2: ( LPAREN ( LPAREN srcCls= IDENT RPAREN )? srcObj= IDENT COMMA ( LPAREN trgCls= IDENT RPAREN )? trgObj= IDENT RPAREN ( 'as' LPAREN role1= IDENT COMMA role2= IDENT RPAREN )? 'in' corrObj= IDENT COLON corrCls= IDENT )
			// /home/pnh/NCKH/workspace-java/RTLPlus/src/org/uet/dse/rtlplus/parser/RTL.g:144:3: LPAREN ( LPAREN srcCls= IDENT RPAREN )? srcObj= IDENT COMMA ( LPAREN trgCls= IDENT RPAREN )? trgObj= IDENT RPAREN ( 'as' LPAREN role1= IDENT COMMA role2= IDENT RPAREN )? 'in' corrObj= IDENT COLON corrCls= IDENT
			{
			match(input,LPAREN,FOLLOW_LPAREN_in_corrLinkDefinition523); 
			// /home/pnh/NCKH/workspace-java/RTLPlus/src/org/uet/dse/rtlplus/parser/RTL.g:145:3: ( LPAREN srcCls= IDENT RPAREN )?
			int alt9=2;
			int LA9_0 = input.LA(1);
			if ( (LA9_0==LPAREN) ) {
				alt9=1;
			}
			switch (alt9) {
				case 1 :
					// /home/pnh/NCKH/workspace-java/RTLPlus/src/org/uet/dse/rtlplus/parser/RTL.g:145:4: LPAREN srcCls= IDENT RPAREN
					{
					match(input,LPAREN,FOLLOW_LPAREN_in_corrLinkDefinition528); 
					srcCls=(Token)match(input,IDENT,FOLLOW_IDENT_in_corrLinkDefinition532); 
					match(input,RPAREN,FOLLOW_RPAREN_in_corrLinkDefinition534); 
					 n.setSourceClass((srcCls!=null?srcCls.getText():null)); 
					}
					break;

			}

			srcObj=(Token)match(input,IDENT,FOLLOW_IDENT_in_corrLinkDefinition544); 
			match(input,COMMA,FOLLOW_COMMA_in_corrLinkDefinition546); 
			 n.setSourceObject((srcObj!=null?srcObj.getText():null)); 
			// /home/pnh/NCKH/workspace-java/RTLPlus/src/org/uet/dse/rtlplus/parser/RTL.g:147:3: ( LPAREN trgCls= IDENT RPAREN )?
			int alt10=2;
			int LA10_0 = input.LA(1);
			if ( (LA10_0==LPAREN) ) {
				alt10=1;
			}
			switch (alt10) {
				case 1 :
					// /home/pnh/NCKH/workspace-java/RTLPlus/src/org/uet/dse/rtlplus/parser/RTL.g:147:4: LPAREN trgCls= IDENT RPAREN
					{
					match(input,LPAREN,FOLLOW_LPAREN_in_corrLinkDefinition553); 
					trgCls=(Token)match(input,IDENT,FOLLOW_IDENT_in_corrLinkDefinition557); 
					match(input,RPAREN,FOLLOW_RPAREN_in_corrLinkDefinition559); 
					 n.setTargetClass((trgCls!=null?trgCls.getText():null)); 
					}
					break;

			}

			trgObj=(Token)match(input,IDENT,FOLLOW_IDENT_in_corrLinkDefinition569); 
			match(input,RPAREN,FOLLOW_RPAREN_in_corrLinkDefinition571); 
			 n.setTargetObject((trgObj!=null?trgObj.getText():null)); 
			// /home/pnh/NCKH/workspace-java/RTLPlus/src/org/uet/dse/rtlplus/parser/RTL.g:149:3: ( 'as' LPAREN role1= IDENT COMMA role2= IDENT RPAREN )?
			int alt11=2;
			int LA11_0 = input.LA(1);
			if ( (LA11_0==45) ) {
				alt11=1;
			}
			switch (alt11) {
				case 1 :
					// /home/pnh/NCKH/workspace-java/RTLPlus/src/org/uet/dse/rtlplus/parser/RTL.g:149:4: 'as' LPAREN role1= IDENT COMMA role2= IDENT RPAREN
					{
					match(input,45,FOLLOW_45_in_corrLinkDefinition579); 
					match(input,LPAREN,FOLLOW_LPAREN_in_corrLinkDefinition581); 
					role1=(Token)match(input,IDENT,FOLLOW_IDENT_in_corrLinkDefinition585); 
					match(input,COMMA,FOLLOW_COMMA_in_corrLinkDefinition587); 
					role2=(Token)match(input,IDENT,FOLLOW_IDENT_in_corrLinkDefinition591); 
					match(input,RPAREN,FOLLOW_RPAREN_in_corrLinkDefinition593); 
					 n.setRoleNames((role1!=null?role1.getText():null), (role2!=null?role2.getText():null)); 
					}
					break;

			}

			match(input,54,FOLLOW_54_in_corrLinkDefinition602); 
			corrObj=(Token)match(input,IDENT,FOLLOW_IDENT_in_corrLinkDefinition606); 
			match(input,COLON,FOLLOW_COLON_in_corrLinkDefinition608); 
			corrCls=(Token)match(input,IDENT,FOLLOW_IDENT_in_corrLinkDefinition612); 
			 n.setName((corrObj!=null?corrObj.getText():null)); n.setClass((corrCls!=null?corrCls.getText():null)); 
			}

		}
		catch (RecognitionException re) {
			reportError(re);
			recover(input,re);
		}
		finally {
			// do for sure before leaving
		}
		return n;
	}
	// $ANTLR end "corrLinkDefinition"

	// Delegated rules



	public static final BitSet FOLLOW_58_in_tggRuleCollection56 = new BitSet(new long[]{0x0000000000100000L});
	public static final BitSet FOLLOW_IDENT_in_tggRuleCollection60 = new BitSet(new long[]{0x0008000000000000L});
	public static final BitSet FOLLOW_51_in_tggRuleCollection68 = new BitSet(new long[]{0x02A4400000000000L});
	public static final BitSet FOLLOW_set_in_tggRuleCollection70 = new BitSet(new long[]{0x0100000000000000L});
	public static final BitSet FOLLOW_tggRuleDefinition_in_tggRuleCollection87 = new BitSet(new long[]{0x0100000000000000L});
	public static final BitSet FOLLOW_EOF_in_tggRuleCollection96 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_56_in_tggRuleDefinition114 = new BitSet(new long[]{0x0000000000100000L});
	public static final BitSet FOLLOW_IDENT_in_tggRuleDefinition118 = new BitSet(new long[]{0x0001000000000000L});
	public static final BitSet FOLLOW_48_in_tggRuleDefinition122 = new BitSet(new long[]{0x0000000004000000L});
	public static final BitSet FOLLOW_ruleDefinition_in_tggRuleDefinition126 = new BitSet(new long[]{0x0002000000000000L});
	public static final BitSet FOLLOW_49_in_tggRuleDefinition130 = new BitSet(new long[]{0x0000000004000000L});
	public static final BitSet FOLLOW_ruleDefinition_in_tggRuleDefinition134 = new BitSet(new long[]{0x0000800000000000L});
	public static final BitSet FOLLOW_47_in_tggRuleDefinition138 = new BitSet(new long[]{0x0000000004000000L});
	public static final BitSet FOLLOW_corrRuleDefinition_in_tggRuleDefinition142 = new BitSet(new long[]{0x0010000000000000L});
	public static final BitSet FOLLOW_52_in_tggRuleDefinition146 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_LPAREN_in_ruleDefinition169 = new BitSet(new long[]{0x0000002004100800L});
	public static final BitSet FOLLOW_patternDefinition_in_ruleDefinition175 = new BitSet(new long[]{0x0000002000000000L});
	public static final BitSet FOLLOW_RPAREN_in_ruleDefinition179 = new BitSet(new long[]{0x0000000000400000L});
	public static final BitSet FOLLOW_LBRACE_in_ruleDefinition181 = new BitSet(new long[]{0x0000000404100800L});
	public static final BitSet FOLLOW_patternDefinition_in_ruleDefinition187 = new BitSet(new long[]{0x0000000400000000L});
	public static final BitSet FOLLOW_RBRACE_in_ruleDefinition191 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_LPAREN_in_corrRuleDefinition214 = new BitSet(new long[]{0x0000002004100000L});
	public static final BitSet FOLLOW_corrPatternDefinition_in_corrRuleDefinition220 = new BitSet(new long[]{0x0000002000000000L});
	public static final BitSet FOLLOW_RPAREN_in_corrRuleDefinition224 = new BitSet(new long[]{0x0000000000400000L});
	public static final BitSet FOLLOW_LBRACE_in_corrRuleDefinition226 = new BitSet(new long[]{0x0000000404100000L});
	public static final BitSet FOLLOW_corrPatternDefinition_in_corrRuleDefinition232 = new BitSet(new long[]{0x0000000400000000L});
	public static final BitSet FOLLOW_RBRACE_in_corrRuleDefinition236 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_objectDefinition_in_patternDefinition267 = new BitSet(new long[]{0x0000000004100802L});
	public static final BitSet FOLLOW_linkDefinition_in_patternDefinition278 = new BitSet(new long[]{0x0000000004000802L});
	public static final BitSet FOLLOW_conditionDefinition_in_patternDefinition289 = new BitSet(new long[]{0x0000000000000802L});
	public static final BitSet FOLLOW_corrLinkDefinition_in_corrPatternDefinition322 = new BitSet(new long[]{0x0000000004100002L});
	public static final BitSet FOLLOW_invariantTGG_in_corrPatternDefinition336 = new BitSet(new long[]{0x0000000000100002L});
	public static final BitSet FOLLOW_IDENT_in_invariantTGG366 = new BitSet(new long[]{0x0000000000000080L});
	public static final BitSet FOLLOW_COLON_in_invariantTGG372 = new BitSet(new long[]{0x0000000000000800L});
	public static final BitSet FOLLOW_conditionDefinition_in_invariantTGG380 = new BitSet(new long[]{0x0000000000000802L});
	public static final BitSet FOLLOW_IDENT_in_objectDefinition409 = new BitSet(new long[]{0x0000000000000080L});
	public static final BitSet FOLLOW_COLON_in_objectDefinition411 = new BitSet(new long[]{0x0000000000100000L});
	public static final BitSet FOLLOW_IDENT_in_objectDefinition415 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_LPAREN_in_linkDefinition442 = new BitSet(new long[]{0x0000000000100000L});
	public static final BitSet FOLLOW_IDENT_in_linkDefinition446 = new BitSet(new long[]{0x0000000000000400L});
	public static final BitSet FOLLOW_COMMA_in_linkDefinition454 = new BitSet(new long[]{0x0000000000100000L});
	public static final BitSet FOLLOW_IDENT_in_linkDefinition458 = new BitSet(new long[]{0x0000002000000400L});
	public static final BitSet FOLLOW_RPAREN_in_linkDefinition465 = new BitSet(new long[]{0x0000000000000080L});
	public static final BitSet FOLLOW_COLON_in_linkDefinition467 = new BitSet(new long[]{0x0000000000100000L});
	public static final BitSet FOLLOW_IDENT_in_linkDefinition473 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_COND_EXPR_in_conditionDefinition496 = new BitSet(new long[]{0x0000000000000002L});
	public static final BitSet FOLLOW_LPAREN_in_corrLinkDefinition523 = new BitSet(new long[]{0x0000000004100000L});
	public static final BitSet FOLLOW_LPAREN_in_corrLinkDefinition528 = new BitSet(new long[]{0x0000000000100000L});
	public static final BitSet FOLLOW_IDENT_in_corrLinkDefinition532 = new BitSet(new long[]{0x0000002000000000L});
	public static final BitSet FOLLOW_RPAREN_in_corrLinkDefinition534 = new BitSet(new long[]{0x0000000000100000L});
	public static final BitSet FOLLOW_IDENT_in_corrLinkDefinition544 = new BitSet(new long[]{0x0000000000000400L});
	public static final BitSet FOLLOW_COMMA_in_corrLinkDefinition546 = new BitSet(new long[]{0x0000000004100000L});
	public static final BitSet FOLLOW_LPAREN_in_corrLinkDefinition553 = new BitSet(new long[]{0x0000000000100000L});
	public static final BitSet FOLLOW_IDENT_in_corrLinkDefinition557 = new BitSet(new long[]{0x0000002000000000L});
	public static final BitSet FOLLOW_RPAREN_in_corrLinkDefinition559 = new BitSet(new long[]{0x0000000000100000L});
	public static final BitSet FOLLOW_IDENT_in_corrLinkDefinition569 = new BitSet(new long[]{0x0000002000000000L});
	public static final BitSet FOLLOW_RPAREN_in_corrLinkDefinition571 = new BitSet(new long[]{0x0040200000000000L});
	public static final BitSet FOLLOW_45_in_corrLinkDefinition579 = new BitSet(new long[]{0x0000000004000000L});
	public static final BitSet FOLLOW_LPAREN_in_corrLinkDefinition581 = new BitSet(new long[]{0x0000000000100000L});
	public static final BitSet FOLLOW_IDENT_in_corrLinkDefinition585 = new BitSet(new long[]{0x0000000000000400L});
	public static final BitSet FOLLOW_COMMA_in_corrLinkDefinition587 = new BitSet(new long[]{0x0000000000100000L});
	public static final BitSet FOLLOW_IDENT_in_corrLinkDefinition591 = new BitSet(new long[]{0x0000002000000000L});
	public static final BitSet FOLLOW_RPAREN_in_corrLinkDefinition593 = new BitSet(new long[]{0x0040000000000000L});
	public static final BitSet FOLLOW_54_in_corrLinkDefinition602 = new BitSet(new long[]{0x0000000000100000L});
	public static final BitSet FOLLOW_IDENT_in_corrLinkDefinition606 = new BitSet(new long[]{0x0000000000000080L});
	public static final BitSet FOLLOW_COLON_in_corrLinkDefinition608 = new BitSet(new long[]{0x0000000000100000L});
	public static final BitSet FOLLOW_IDENT_in_corrLinkDefinition612 = new BitSet(new long[]{0x0000000000000002L});
}

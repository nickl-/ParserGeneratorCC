/* Generated By:JavaCC: Do not edit this line. JJTreeParserVisitor.java Version 4.1d1 */
package com.helger.pgcc.jjtree;

public interface JJTreeParserVisitor
{
  Object visit (SimpleNode node, Object data);

  Object visit (ASTGrammar node, Object data);

  Object visit (ASTCompilationUnit node, Object data);

  Object visit (ASTProductions node, Object data);

  Object visit (ASTOptions node, Object data);

  Object visit (ASTOptionBinding node, Object data);

  Object visit (ASTJavacode node, Object data);

  Object visit (ASTJavacodeBody node, Object data);

  Object visit (ASTBNF node, Object data);

  Object visit (ASTBNFDeclaration node, Object data);

  Object visit (ASTBNFNodeScope node, Object data);

  Object visit (ASTRE node, Object data);

  Object visit (ASTTokenDecls node, Object data);

  Object visit (ASTRESpec node, Object data);

  Object visit (ASTBNFChoice node, Object data);

  Object visit (ASTBNFSequence node, Object data);

  Object visit (ASTBNFLookahead node, Object data);

  Object visit (ASTExpansionNodeScope node, Object data);

  Object visit (ASTBNFAction node, Object data);

  Object visit (ASTBNFZeroOrOne node, Object data);

  Object visit (ASTBNFTryBlock node, Object data);

  Object visit (ASTBNFNonTerminal node, Object data);

  Object visit (ASTBNFAssignment node, Object data);

  Object visit (ASTBNFOneOrMore node, Object data);

  Object visit (ASTBNFZeroOrMore node, Object data);

  Object visit (ASTBNFParenthesized node, Object data);

  Object visit (ASTREStringLiteral node, Object data);

  Object visit (ASTRENamed node, Object data);

  Object visit (ASTREReference node, Object data);

  Object visit (ASTREEOF node, Object data);

  Object visit (ASTREChoice node, Object data);

  Object visit (ASTRESequence node, Object data);

  Object visit (ASTREOneOrMore node, Object data);

  Object visit (ASTREZeroOrMore node, Object data);

  Object visit (ASTREZeroOrOne node, Object data);

  Object visit (ASTRRepetitionRange node, Object data);

  Object visit (ASTREParenthesized node, Object data);

  Object visit (ASTRECharList node, Object data);

  Object visit (ASTCharDescriptor node, Object data);

  Object visit (ASTNodeDescriptor node, Object data);

  Object visit (ASTNodeDescriptorExpression node, Object data);

  Object visit (ASTPrimaryExpression node, Object data);
}
/*
 * JavaCC - OriginalChecksum=236a0da55ea23f741ece2c8012f6d143 (do not edit this
 * line)
 */
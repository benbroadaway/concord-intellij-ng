package brig.concord.psi.impl.yaml;

import com.intellij.lang.ASTNode;
import com.intellij.openapi.util.NlsSafe;
import com.intellij.psi.*;
import com.intellij.psi.search.GlobalSearchScope;
import com.intellij.psi.search.SearchScope;
import com.intellij.util.IncorrectOperationException;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.yaml.psi.YAMLScalar;
import org.jetbrains.yaml.psi.impl.YAMLPlainTextImpl;

public class YAMLConcordPlainTextImpl extends YAMLPlainTextImpl implements PsiNamedElement, PsiLanguageInjectionHost, YAMLScalar {

    public YAMLConcordPlainTextImpl(@NotNull ASTNode node) {
        super(node);
    }

    @Override
    public @NotNull SearchScope getUseScope() {
        PsiFile containingFile = getContainingFile();
        return containingFile != null ? GlobalSearchScope.fileScope(containingFile) : GlobalSearchScope.EMPTY_SCOPE;
    }

    private transient PsiNamedElement delegate;

//    private PsiNamedElement getDelegate() {
//        if (delegate == null) {
//            delegate = ConcordYamlDelegateFactory.createDelegate(this);
//        }
//        return delegate;
//    }

    @Override
    public PsiReference @NotNull [] getReferences() {
//        return getDelegate().getReferences();
        return super.getReferences();
    }

    @Override
    public PsiElement setName(@NlsSafe @NotNull String name) throws IncorrectOperationException {
//        return getDelegate().setName(name);
        return this;
    }

    @Override
    public String getName() {
//        return getDelegate().getName();
        return super.getName();
    }

    @Override
    public void delete() throws IncorrectOperationException {
//        getDelegate().delete();
        super.delete();
    }
}

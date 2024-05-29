package com.lebastudios.sealcodeplugins.completations;

import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;

public class ClassOrInterfceCompletation extends JavaNodeCompletation
{
    private final ClassOrInterfaceDeclaration classe;
    
    public ClassOrInterfceCompletation(ClassOrInterfaceDeclaration classe)
    {
        super(classe.getNameAsString(), "class.jpg");
        
        this.classe = classe;
    }

    @Override
    public String getDescription()
    {
        return "Class";
    }

    @Override
    public String getCompletation()
    {
        return getValue();
    }

    @Override
    public TreeSet<JavaNodeCompletation> getChildren()
    {
        List<JavaNodeCompletation> completations = new ArrayList<>();
        
        for (var member : classe.getMembers())
        {
            JavaNodeCompletation nodeCompletation = toJavaNode(member);
            
            if (nodeCompletation != null) 
            {
                completations.add(nodeCompletation);
            }
        }
        
        for (var fields : classe.getFields())
        {
            for (var variable : fields.getVariables())
            {
                JavaNodeCompletation nodeCompletation = toJavaNode(variable);

                if (nodeCompletation != null)
                {
                    completations.add(nodeCompletation);
                }
            }
        }
        
        return new TreeSet<>(completations);
    }
}

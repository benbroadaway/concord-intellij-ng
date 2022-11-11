package brig.concord.meta.model;

import org.jetbrains.yaml.meta.model.YamlArrayType;

public class DependenciesMetaType extends YamlArrayType {

    private static final DependenciesMetaType INSTANCE = new DependenciesMetaType();

    public static DependenciesMetaType getInstance() {
        return INSTANCE;
    }

    private DependenciesMetaType() {
        super(DependencyMetaType.getInstance());
    }
}

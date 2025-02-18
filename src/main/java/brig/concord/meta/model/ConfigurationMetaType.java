package brig.concord.meta.model;

import brig.concord.meta.ConcordAnyMapMetaType;
import brig.concord.meta.ConcordMetaType;
import brig.concord.meta.ConcordStringArrayMetaType;
import com.intellij.codeInspection.ProblemsHolder;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.yaml.meta.model.*;
import org.jetbrains.yaml.psi.YAMLValue;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public class ConfigurationMetaType extends ConcordMetaType {

    private static final ConfigurationMetaType INSTANCE = new ConfigurationMetaType();

    private static final Map<String, Supplier<YamlMetaType>> features = new HashMap<>();

    static {
        features.put("runtime", () -> new YamlEnumType("runtime").withLiterals("concord-v2"));
        features.put("debug", YamlBooleanType::getSharedInstance);
        features.put("entryPoint", YamlStringType::getInstance);
        features.put("dependencies", DependenciesMetaType::getInstance);
        features.put("arguments", ConcordAnyMapMetaType::getInstance);
        features.put("meta", ConcordAnyMapMetaType::getInstance);
        features.put("events", EventsMetaType::getInstance);
        features.put("requirements", ConcordAnyMapMetaType::getInstance);
        features.put("processTimeout", DurationMetaType::getInstance);
        features.put("suspendTimeout", DurationMetaType::getInstance);
        features.put("exclusive", ExclusiveMetaType::getInstance);
        features.put("out", ConcordStringArrayMetaType::getInstance);
        features.put("template", YamlStringType::getInstance);
        features.put("parallelLoopParallelism", () -> YamlIntegerType.getInstance(false));
    }

    public static ConfigurationMetaType getInstance() {
        return INSTANCE;
    }

    private ConfigurationMetaType() {
        super("Configuration");
    }

    @Override
    protected Map<String, Supplier<YamlMetaType>> getFeatures() {
        return features;
    }

    @Override
    public void validateValue(@NotNull YAMLValue value, @NotNull ProblemsHolder problemsHolder) {

    }
}

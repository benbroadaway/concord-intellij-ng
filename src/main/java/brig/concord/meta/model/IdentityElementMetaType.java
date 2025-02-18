package brig.concord.meta.model;

import brig.concord.psi.YamlPsiUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.yaml.meta.model.Field;
import org.jetbrains.yaml.meta.model.YamlAnyOfType;
import org.jetbrains.yaml.meta.model.YamlMetaType;
import org.jetbrains.yaml.psi.YAMLKeyValue;
import org.jetbrains.yaml.psi.YAMLMapping;
import org.jetbrains.yaml.psi.YAMLValue;

import java.util.*;

public class IdentityElementMetaType extends YamlAnyOfType {

    private final List<IdentityMetaType> entries;

    protected IdentityElementMetaType(String typeName, List<IdentityMetaType> entries) {
        super(typeName, List.copyOf(entries));

        this.entries = entries;
    }

    @Override
    public @Nullable Field findFeatureByName(@NotNull String name) {
        IdentityMetaType meta = findEntry(Set.of(name));
        if (meta == null) {
            return null;
        }

        return new Field(name, new Field.MetaTypeSupplier() {

            @Override
            public @NotNull YamlMetaType getMainType() {
                return IdentityElementMetaType.this;
            }

            @Override
            public @Nullable YamlMetaType getSpecializedType(@NotNull YAMLValue element) {
                YAMLMapping m = YamlPsiUtils.getParentOfType(element, YAMLMapping.class, false);
                if (m == null) {
                    return null;
                }

                IdentityMetaType meta = findEntry(YamlPsiUtils.keys(m));
                if (meta == null) {
                    return null;
                }

                YAMLKeyValue kv = YamlPsiUtils.getParentOfType(element, YAMLKeyValue.class, false);
                if (kv == null) {
                    return null;
                }

                Field field = meta.findFeatureByName(kv.getKeyText());
                if (field != null) {
                    return field.getDefaultType();
                }

                return null;
            }
        });
    }

    @Override
    public @NotNull List<String> computeMissingFields(@NotNull Set<String> existingFields) {
        IdentityMetaType stepMeta = identifyEntry(existingFields);
        if (stepMeta == null) {
            return Collections.emptyList();
        }
        return stepMeta.computeMissingFields(existingFields);
    }

    @Override
    public @NotNull List<Field> computeKeyCompletions(@Nullable YAMLMapping existingMapping) {
        IdentityMetaType stepMeta = Optional.ofNullable(existingMapping)
                        .map(YamlPsiUtils::keys)
                        .map(this::identifyEntry)
                .orElse(null);

        if (stepMeta != null) {
            return stepMeta.computeKeyCompletions(existingMapping);
        }

        Set<String> processedNames = new HashSet<>();
        LinkedHashSet<Field> result = new LinkedHashSet<>();
        for (IdentityMetaType e : entries) {
            String identity = e.getIdentity();
            if (!processedNames.contains(identity)) {
                processedNames.add(identity);
                result.add(e.findFeatureByName(identity));
            }
        }
        return new LinkedList<>(result);
    }

    private IdentityMetaType identifyEntry(Set<String> existingKeys) {
        for (IdentityMetaType step : entries) {
            if (existingKeys.contains(step.getIdentity())) {
                return step;
            }
        }

        return null;
    }

    private IdentityMetaType findEntry(Set<String> existingKeys) {
        IdentityMetaType result = identifyEntry(existingKeys);
        if (result != null) {
            return result;
        }

        int maxMatches = 0;
        for (IdentityMetaType s : entries) {
            int matches = 0;
            Set<String> features = s.getFeatures().keySet();
            for (String k : existingKeys) {
                if (features.contains(k)) {
                    matches++;
                }
            }
            if (matches > maxMatches) {
                maxMatches = matches;
                result = s;
            }
        }

        return result;
    }
}

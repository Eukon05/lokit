package ovh.eukon05.lokit.identityservice.adapter;

import java.util.List;
import java.util.Optional;

public interface IdpAdapter {
    Optional<IdpUserMetadata> getUserMetadata(String userIdpId);

    List<IdpUserMetadata> getAllUsers();
}

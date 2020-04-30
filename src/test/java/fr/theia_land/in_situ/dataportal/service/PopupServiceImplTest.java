package fr.theia_land.in_situ.dataportal.service;

import fr.theia_land.in_situ.dataportal.model.POJO.popup.litePopup.PopupContent;
import fr.theia_land.in_situ.dataportal.repository.ObservationDocumentLiteRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.*;

@SpringBootTest
class PopupServiceImplTest {
    @Mock
    ObservationDocumentLiteRepository observationDocumentLiteRepositoryMock;
    @InjectMocks
    PopupServiceImpl service;

    @Test
    void getPopupContent() {
        when(observationDocumentLiteRepositoryMock.loadPopupContent(anyList()))
                .thenReturn(new PopupContent());
        List<String> payload = new ArrayList<>();
        payload.add("test");
        PopupContent popupContent = service.getPopupContent(payload);
        assertThat(popupContent).isExactlyInstanceOf(PopupContent.class);
        verify(observationDocumentLiteRepositoryMock, times(1)).loadPopupContent(anyList());
    }
}
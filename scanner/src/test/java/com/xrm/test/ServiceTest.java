package com.xrm.test;

import com.xrm.domain.ChangeSet;
import com.xrm.domain.Project;
import com.xrm.repositories.ChangeSetRepository;
import com.xrm.repositories.ProjectRepository;
import com.xrm.repositories.ProjectUpdateRepository;
import com.xrm.services.BuildProcessingService;
import com.xrm.services.ClassLoaderFactory;
import com.xrm.utils.ClassExtractor;
import com.xrm.utils.HashSumFactory;
import org.aspectj.apache.bcel.util.ByteSequence;
import org.assertj.core.util.Lists;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;


@RunWith(SpringRunner.class)
public class ServiceTest {

    private static final String TEST_FILE_NAME = "test.jar";
    private static final byte[] FILE_CONTENT = new byte[]{0, 0, 0, 0}.clone();
    private static final String FAKE_CHECKSUM = "ABCDCDBA";
    //    @Autowired
    BuildProcessingService processingService;

    @MockBean
    private ProjectUpdateRepository projectUpdatesRepository;
    @MockBean
    private HashSumFactory hashSumFactory;
    @MockBean
    private ProjectRepository projectRepository;
    @MockBean
    private ChangeSetRepository changeSetRepository;
    @MockBean
    private ClassExtractor classExtractor;

    @MockBean
    MultipartFile multipartFile;

    @Before
    public void setUp() throws Exception {
        processingService = new BuildProcessingService();
        ReflectionTestUtils.setField(processingService,"projectUpdatesRepository",projectUpdatesRepository);
        ReflectionTestUtils.setField(processingService,"hashSumFactory",hashSumFactory);
        ReflectionTestUtils.setField(processingService,"projectRepository",projectRepository);
        ReflectionTestUtils.setField(processingService,"changeSetRepository",changeSetRepository);
        ReflectionTestUtils.setField(processingService,"classExtractor",classExtractor);

    }

    @MockBean
    ClassLoaderFactory loaderFactory;
    @Test
    public void testProcessingService() throws Exception {



        given(projectRepository.findOneByNameAndFileName(any(),any())).willReturn(Optional.empty());

        given(multipartFile.getOriginalFilename()).willReturn(TEST_FILE_NAME);
        given(multipartFile.getBytes()).willReturn(FILE_CONTENT);
        given(multipartFile.isEmpty()).willReturn(false);
        given(multipartFile.getInputStream()).willReturn(new ByteSequence(FILE_CONTENT));

        given(hashSumFactory.getMD5Checksum(any())).willReturn(FAKE_CHECKSUM);

        given(changeSetRepository.findByFileHash(FAKE_CHECKSUM)).willReturn(Optional.empty());


        given(classExtractor.extractClasses(any())).willReturn(Lists.emptyList());

        processingService.process(multipartFile);

        verify(projectRepository).findOneByNameAndFileName(any(),any());
        verify(projectRepository,times(1)).save(any(Project.class));
        verify( changeSetRepository,times(1)).save(any(ChangeSet.class));
        verify(classExtractor,times(1)).extractClasses(any());

    }
}

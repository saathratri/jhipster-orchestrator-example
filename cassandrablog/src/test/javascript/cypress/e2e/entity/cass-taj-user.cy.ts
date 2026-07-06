import {
  entityConfirmDeleteButtonSelector,
  entityCreateButtonSelector,
  entityCreateCancelButtonSelector,
  entityCreateSaveButtonSelector,
  entityDeleteButtonSelector,
  entityDetailsBackButtonSelector,
  entityDetailsButtonSelector,
  entityEditButtonSelector,
  entityTableSelector,
} from '../../support/entity';

describe('CassTajUser e2e test', () => {
  const cassTajUserPageUrl = '/cassandrablog/cass-taj-user';
  const cassTajUserPageUrlPattern = new RegExp('/cassandrablog/cass-taj-user(\\?.*)?$');
  let username: string;
  let password: string;
  const cassTajUserSample = { id: '00000000-0000-4000-8000-000000000001', login: 'supposing sup plus' };

  let cassTajUser;

  before(() => {
    cy.credentials().then(credentials => {
      ({ username, password } = credentials);
    });
  });

  beforeEach(() => {
    cy.login(username, password);
  });

  beforeEach(() => {
    cy.intercept('GET', /^\/services\/cassandrablog\/api\/cass-taj-users\b/).as('entitiesRequest');
    cy.intercept('POST', '/services/cassandrablog/api/cass-taj-users').as('postEntityRequest');
    cy.intercept('DELETE', '/services/cassandrablog/api/cass-taj-users/*').as('deleteEntityRequest');
  });

  afterEach(() => {
    if (cassTajUser) {
      cy.authenticatedRequest({
        method: 'DELETE',
        url: `/services/cassandrablog/api/cass-taj-users/${cassTajUser.id}`,
      }).then(() => {
        cassTajUser = undefined;
      });
    }
  });

  it('CassTajUsers menu should load CassTajUsers page', () => {
    cy.visit('/');
    cy.clickOnEntityMenuItem('cassandrablog/cass-taj-user');
    cy.wait('@entitiesRequest', { timeout: 30000 }).then(({ response }) => {
      if (response?.body.length === 0) {
        cy.get(entityTableSelector).should('not.exist');
      } else {
        cy.get(entityTableSelector).should('exist');
      }
    });
    cy.getEntityHeading('CassTajUser').should('exist');
    cy.url().should('match', cassTajUserPageUrlPattern);
  });

  describe('CassTajUser page', () => {
    it('should have translated page title', () => {
      cy.visit(cassTajUserPageUrl);
      cy.getEntityHeading('CassTajUser').should('not.contain', 'cassandrablogApp.cassandrablogCassTajUser.home.title');
    });

    describe('create button click', () => {
      beforeEach(() => {
        cy.visit(cassTajUserPageUrl);
        cy.wait('@entitiesRequest', { timeout: 30000 });
      });

      it('should load create CassTajUser page', () => {
        cy.get(entityCreateButtonSelector).click();
        cy.url().should('match', new RegExp('/cassandrablog/cass-taj-user/new$'));
        cy.getEntityCreateUpdateHeading('CassTajUser');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest', { timeout: 30000 }).then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', cassTajUserPageUrlPattern);
      });
    });

    describe('with existing value', () => {
      beforeEach(() => {
        cy.authenticatedRequest({
          method: 'POST',
          url: '/services/cassandrablog/api/cass-taj-users',
          body: cassTajUserSample,
        }).then(({ body }) => {
          cassTajUser = body;

          cy.intercept(
            {
              method: 'GET',
              url: /^\/services\/cassandrablog\/api\/cass-taj-users\b/,
              times: 1,
            },
            {
              statusCode: 200,
              body: [cassTajUser],
            },
          ).as('entitiesRequestInternal');
        });

        cy.visit(cassTajUserPageUrl);

        cy.wait('@entitiesRequestInternal', { timeout: 30000 });
      });

      it('detail button click should load details CassTajUser page', () => {
        cy.get(entityDetailsButtonSelector).first().click();
        cy.getEntityDetailsHeading('cassTajUser');
        cy.get(entityDetailsBackButtonSelector).click();
        cy.wait('@entitiesRequest', { timeout: 30000 }).then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', cassTajUserPageUrlPattern);
      });

      it('edit button click should load edit CassTajUser page and go back', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('CassTajUser');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest', { timeout: 30000 }).then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', cassTajUserPageUrlPattern);
      });

      it('edit button click should load edit CassTajUser page and save', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('CassTajUser');
        cy.get(entityCreateSaveButtonSelector).click();
        cy.wait('@entitiesRequest', { timeout: 30000 }).then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', cassTajUserPageUrlPattern);
      });

      it('last delete button click should delete instance of CassTajUser', () => {
        cy.get(entityDeleteButtonSelector).last().click();
        cy.getEntityDeleteDialogHeading('cassTajUser').should('exist');
        cy.get(entityConfirmDeleteButtonSelector).click();
        cy.wait('@deleteEntityRequest').then(({ response }) => {
          expect(response?.statusCode).to.equal(204);
        });
        cy.wait('@entitiesRequest', { timeout: 30000 }).then(({ response }) => {
          expect(response?.statusCode).to.equal(200);
        });
        cy.url().should('match', cassTajUserPageUrlPattern);

        cassTajUser = undefined;
      });
    });
  });

  describe('new CassTajUser page', () => {
    beforeEach(() => {
      cy.visit(cassTajUserPageUrl);
      cy.get(entityCreateButtonSelector).click();
      cy.getEntityCreateUpdateHeading('CassTajUser');
    });

    it('should create an instance of CassTajUser', () => {
      cy.get(`[data-cy="id"]`).type('00000000-0000-4000-8000-000000000001');
      cy.get(`[data-cy="id"]`).should('have.value', '00000000-0000-4000-8000-000000000001');

      cy.get(`[data-cy="login"]`).type('uh-huh where splendid');
      cy.get(`[data-cy="login"]`).should('have.value', 'uh-huh where splendid');

      cy.get(entityCreateSaveButtonSelector).click();

      cy.wait('@postEntityRequest').then(({ response }) => {
        expect(response?.statusCode).to.equal(201);
        cassTajUser = response.body;
      });
      cy.wait('@entitiesRequest', { timeout: 30000 }).then(({ response }) => {
        expect(response?.statusCode).to.equal(200);
      });
      cy.url().should('match', cassTajUserPageUrlPattern);
    });
  });

  it('should generate and reset a UUID via the form buttons', () => {
    cy.visit('/');
    cy.clickOnEntityMenuItem(cassTajUserPageUrl.substring(1));
    cy.get(entityCreateButtonSelector, { timeout: 30000 }).click();
    // Generate fills a fresh UUID via the component's generateUUID()/generateTimeUUID().
    cy.get(`[data-cy="id-generate"]`).click();
    cy.get(`[data-cy="id"]`)
      .invoke('val')
      .should('match', /^[0-9a-f]{8}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{12}$/i);
    // Reset restores the (empty) saved value, clearing the field.
    cy.get(`[data-cy="id-reset"]`).click();
    cy.get(`[data-cy="id"]`).should('have.value', '');
  });
});

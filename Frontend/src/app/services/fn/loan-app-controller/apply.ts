/* tslint:disable */
/* eslint-disable */
/* Code generated by ng-openapi-gen DO NOT EDIT. */

import { HttpClient, HttpContext, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { StrictHttpResponse } from '../../strict-http-response';
import { RequestBuilder } from '../../request-builder';

import { LoanApp } from '../../models/loan-app';

export interface Apply$Params {
      body: LoanApp
}

export function apply(http: HttpClient, rootUrl: string, params: Apply$Params, context?: HttpContext): Observable<StrictHttpResponse<LoanApp>> {
  const rb = new RequestBuilder(rootUrl, apply.PATH, 'post');
  if (params) {
    rb.body(params.body, 'application/json');
  }

  return http.request(
    rb.build({ responseType: 'json', accept: 'application/json', context })
  ).pipe(
    filter((r: any): r is HttpResponse<any> => r instanceof HttpResponse),
    map((r: HttpResponse<any>) => {
      return r as StrictHttpResponse<LoanApp>;
    })
  );
}

apply.PATH = '/loan/apply';

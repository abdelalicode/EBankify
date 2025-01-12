/* tslint:disable */
/* eslint-disable */
/* Code generated by ng-openapi-gen DO NOT EDIT. */

import { HttpClient, HttpContext, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { StrictHttpResponse } from '../../strict-http-response';
import { RequestBuilder } from '../../request-builder';

import { AssetDto } from '../../models/asset-dto';
import { AssetResponseDto } from '../../models/asset-response-dto';

export interface CreateAsset$Params {
      body: AssetDto
}

export function createAsset(http: HttpClient, rootUrl: string, params: CreateAsset$Params, context?: HttpContext): Observable<StrictHttpResponse<AssetResponseDto>> {
  const rb = new RequestBuilder(rootUrl, createAsset.PATH, 'post');
  if (params) {
    rb.body(params.body, 'application/json');
  }

  return http.request(
    rb.build({ responseType: 'json', accept: 'application/json', context })
  ).pipe(
    filter((r: any): r is HttpResponse<any> => r instanceof HttpResponse),
    map((r: HttpResponse<any>) => {
      return r as StrictHttpResponse<AssetResponseDto>;
    })
  );
}

createAsset.PATH = '/assets';

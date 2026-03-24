export interface StatsDashboardContentItem {
    description: string;
}

export interface StatsDashboardAction {
    nav: string;
}

export interface StatsDashboardItem {
    name: string;
    title: string;
    subtitle: string;
    content: StatsDashboardContentItem[];
    actions: StatsDashboardAction[];
    key: string;
}

export type StatsDashboard = StatsDashboardItem[];
